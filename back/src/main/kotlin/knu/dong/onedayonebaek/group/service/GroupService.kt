package knu.dong.onedayonebaek.group.service

import io.github.oshai.kotlinlogging.KotlinLogging
import knu.dong.onedayonebaek.common.exception.ConflictException
import knu.dong.onedayonebaek.common.exception.ForbiddenException
import knu.dong.onedayonebaek.common.exception.NotFoundException
import knu.dong.onedayonebaek.containgroup.domain.ContainGroup
import knu.dong.onedayonebaek.containgroup.repository.ContainGroupRepository
import knu.dong.onedayonebaek.group.dto.*
import knu.dong.onedayonebaek.group.repository.GroupRepository
import knu.dong.onedayonebaek.problem.domain.Problem
import knu.dong.onedayonebaek.problem.dto.ProblemsOfUser
import knu.dong.onedayonebaek.problem.dto.toProblemDto
import knu.dong.onedayonebaek.problem.repository.ProblemRepository
import knu.dong.onedayonebaek.user.domain.User
import knu.dong.onedayonebaek.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth

private val myLogger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class GroupService (
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val containGroupRepository: ContainGroupRepository,
    private val problemRepository: ProblemRepository,
    private val passwordEncoder: PasswordEncoder
){
    fun getGroups(user: User): List<GroupOfListDto> {
        val userGroupIds = containGroupRepository.findAllByUser(user).map { it.group.id }

        return groupRepository.findAll().map { it.toGroupOfListDto(isMember = (it.id in userGroupIds)) }
    }


    fun getGroupsOfUser(user: User): List<GroupOfListDto> =
        containGroupRepository.findAllByUser(user).map { it.group.toGroupOfListDto(isMember = true) }

    fun getGroupDetail(user: User, groupId: Long): GroupDetailDto {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }
        val isMember = containGroupRepository.existsByGroupAndUser(group, user)

        if (!group.isPrivate || isMember) {
            return group.toGroupDetailDto(isMember = isMember)
        }

        throw ForbiddenException(message = "해당 비밀 그룹에 속해있지 않습니다.")
    }

    @Transactional
    fun createGroup(user: User, req: CreateOrUpdateGroupRequest): GroupDetailDto {
        if (!req.isPrivate) {
            req.password = null
        }
        else if (req.password != null) {
            val encodedPW = passwordEncoder.encode(req.password)
            req.password = encodedPW
        }

        val inviteCode = getRandomInviteCode(6)

        val group = req.toEntity(inviteCode, user)
        val containGroup = ContainGroup(user = user, group = group)

        val createdGroup = groupRepository.save(group)
        val createdCG = containGroupRepository.save(containGroup)

        createdGroup.users.add(createdCG)

        return group.toGroupDetailDto(isMember = true)
    }

    @Transactional
    fun updateGroup(user: User, groupId: Long, updateReq: CreateOrUpdateGroupRequest): GroupDetailDto {
        val existingGroup = groupRepository.findById(groupId).orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }

        if (existingGroup.owner.id != user.id) {
            throw ForbiddenException(message = "그룹 장만 수정할 수 있습니다.")
        }

        if (updateReq.password != null) {
            val encodedPW = passwordEncoder.encode(updateReq.password)
            updateReq.password = encodedPW
        }

        existingGroup.isPrivate = updateReq.isPrivate
        existingGroup.name = updateReq.name
        existingGroup.password = if (updateReq.isPrivate) updateReq.password else null
        existingGroup.goalSolveCount = updateReq.goalSolveCount
        existingGroup.fine = updateReq.fine

        val updatedGroup = groupRepository.save(existingGroup)

        return updatedGroup.toGroupDetailDto(isMember = true)
    }

    @Transactional
    fun joinGroup(user: User, groupId: Long, password: String?): GroupDetailDto {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }

        if (containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ConflictException("already_joined", "이미 가입된 스터디 그룹입니다.")
        }

        if (!group.isPrivate) {
            containGroupRepository.save(ContainGroup(user, group))

            return group.toGroupDetailDto(isMember = true)
        }

        if (password == null) {
            throw ConflictException("incorrect_password", "비밀번호가 다릅니다.")
        }

        val isCorrectPW = passwordEncoder.matches(password, group.password)

        if (isCorrectPW) {
            containGroupRepository.save(ContainGroup(user, group))

            return group.toGroupDetailDto(isMember = true)
        }

        throw ConflictException("incorrect_password", "비밀번호가 다릅니다.")
    }

    @Transactional
    fun joinGroupWithInviteCode(user: User, inviteCode: String): GroupDetailDto {
        val group = groupRepository
            .findByInviteCode(inviteCode)
            .orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }

        if (containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ConflictException("already_joined", "이미 가입된 스터디 그룹입니다.")
        }

        containGroupRepository.save(ContainGroup(user, group))

        return group.toGroupDetailDto(isMember = true)
    }

    @Transactional
    fun leaveGroup(user: User, groupId: Long) {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }
        if (group.owner.id == user.id) {
            throw ConflictException("not_allowed_leave_group_owner", "그룹 장은 나갈 수 없습니다.")
        }

        val containGroup =
            containGroupRepository
                .findByGroupAndUser(group, user)
                .orElseThrow{ ConflictException("already_leaved", "해당 스터디 그룹에 속해있지 않습니다.") }

        containGroupRepository.delete(containGroup)
    }

    @Transactional
    fun kickGroupUser(owner: User, groupId: Long, targetUserId: Long) {
        val group = groupRepository.findById(groupId)
            .orElseThrow{ NotFoundException(code = "not_found_group", message = "해당 그룹이 없습니다.") }
        if (group.owner.id != owner.id) {
            throw ForbiddenException(message = "관리자만 내보낼 수 있습니다.")
        }
        if (owner.id == targetUserId) {
            throw ConflictException(message = "본인은 내보낼 수 없습니다.")
        }

        val targetUser = userRepository.findById(targetUserId)
            .orElseThrow { NotFoundException(code = "not_found_user", message = "해당 유저가 없습니다.") }

        containGroupRepository
            .findByGroupAndUser(group, targetUser)
            .ifPresent { containGroupRepository.delete(it) }
    }

    fun getProblems(user: User, groupId: Long, date: LocalDate): List<ProblemsOfUser> {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }
        if (group.isPrivate && !containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ForbiddenException(message = "해당 비밀 그룹에 속해있지 않습니다.")
        }

        val users = containGroupRepository.findAllByGroup(group).map { it.user }

        val problems = problemRepository.findAllBySolvedDateAndUserIsIn(date, users)

        return mapUserProblem(users, problems)
    }

    fun getProblems(user: User, groupId: Long, yearMonth: YearMonth): List<ProblemsOfUser> {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }
        if (group.isPrivate && !containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ForbiddenException(message = "해당 비밀 그룹에 속해있지 않습니다.")
        }

        val users = containGroupRepository.findAllByGroup(group).map { it.user }
        val start = yearMonth.atDay(1)
        val end = yearMonth.atEndOfMonth()

        val problems = problemRepository.findAllBySolvedDateBetweenAndUserIsIn(start, end, users)

        return mapUserProblem(users, problems)
    }

    fun getProblems(user: User, groupId: Long, startDate: LocalDate, endDate: LocalDate): List<ProblemsOfUser> {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException(message = "해당 그룹이 없습니다.") }
        if (group.isPrivate && !containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ForbiddenException(message = "해당 비밀 그룹에 속해있지 않습니다.")
        }

        val users = containGroupRepository.findAllByGroup(group).map { it.user }
        val problems = problemRepository.findAllBySolvedDateBetweenAndUserIsIn(startDate, endDate, users)

        return mapUserProblem(users, problems)
    }

    private fun getRandomInviteCode(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    private fun mapUserProblem(users: List<User>, problems: List<Problem>): List<ProblemsOfUser> {
        val userMap = mutableMapOf<Long, ProblemsOfUser>()

        users.forEach { userMap[it.id!!] = ProblemsOfUser(arrayListOf(), it.name, it.id) }
        problems.forEach { userMap[it.user.id]?.problems?.add(it.toProblemDto()) }

        return userMap.values.toList()
    }
}