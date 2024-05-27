package knu.dong.onedayonebaek.group.service

import io.github.oshai.kotlinlogging.KotlinLogging
import knu.dong.onedayonebaek.containgroup.domain.ContainGroup
import knu.dong.onedayonebaek.problem.domain.Problem
import knu.dong.onedayonebaek.user.domain.User
import knu.dong.onedayonebaek.dto.*
import knu.dong.onedayonebaek.common.exception.ConflictException
import knu.dong.onedayonebaek.common.exception.ForbiddenException
import knu.dong.onedayonebaek.common.exception.NotFoundException
import knu.dong.onedayonebaek.group.repository.GroupRepository
import knu.dong.onedayonebaek.containgroup.repository.ContainGroupRepository
import knu.dong.onedayonebaek.group.dto.*
import knu.dong.onedayonebaek.problem.dto.ProblemsOfUser
import knu.dong.onedayonebaek.problem.dto.toProblemDto
import knu.dong.onedayonebaek.problem.repository.ProblemRepository
import knu.dong.onedayonebaek.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth
import java.util.stream.Collectors

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
    fun getGroups(): List<GroupOfListDto> =
        groupRepository.findAll()
            .stream()
            .map { it.toGroupOfListDto() }
            .collect(Collectors.toList())

    fun getGroupDetail(user: User, groupId: Long): GroupDetailDto {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }

        if (!group.isPrivate || containGroupRepository.existsByGroupAndUser(group, user)) {
            return group.toGroupDetailDto()
        }

        throw ForbiddenException("해당 비밀 그룹에 속해있지 않습니다.")
    }

    @Transactional
    fun createGroup(user: User, req: CreateGroupRequest): GroupDetailDto {
        if (req.password != null) {
            val encodedPW = passwordEncoder.encode(req.password)
            req.password = encodedPW
        }

        val inviteCode = getRandomInviteCode(6)

        val group = req.toEntity(inviteCode, user)
        val containGroup = ContainGroup(user = user, group = group)

        val createdGroup = groupRepository.save(group)
        val createdCG = containGroupRepository.save(containGroup)

        createdGroup.users.add(createdCG)

        return group.toGroupDetailDto()
    }

    @Transactional
    fun joinGroup(user: User, groupId: Long, password: String?): GroupDetailDto {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }

        if (containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ConflictException("already_joined", "이미 가입된 스터디 그룹입니다.")
        }

        if (!group.isPrivate) {
            containGroupRepository.save(ContainGroup(user, group))

            return group.toGroupDetailDto()
        }

        if (password == null) {
            throw ConflictException("incorrect_password", "비밀번호가 다릅니다.")
        }

        val isCorrectPW = passwordEncoder.matches(password, group.password)

        if (isCorrectPW) {
            containGroupRepository.save(ContainGroup(user, group))

            return group.toGroupDetailDto()
        }

        throw ConflictException("incorrect_password", "비밀번호가 다릅니다.")
    }

    @Transactional
    fun joinGroupWithInviteCode(user: User, inviteCode: String): GroupDetailDto {
        val group = groupRepository
            .findByInviteCode(inviteCode)
            .orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }

        if (containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ConflictException("already_joined", "이미 가입된 스터디 그룹입니다.")
        }

        containGroupRepository.save(ContainGroup(user, group))

        return group.toGroupDetailDto()
    }

    @Transactional
    fun leaveGroup(user: User, groupId: Long) {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }
        if (group.owner.id == user.id) {
            throw ConflictException("not_allowed_leave_group_owner", "그룹 장은 나갈 수 없습니다.")
        }

        val containGroup =
            containGroupRepository
                .findByGroupAndUser(group, user)
                .orElseThrow{ ConflictException("already_leaved", "해당 스터디 그룹에 속해있지 않습니다.") }

        containGroupRepository.delete(containGroup)
    }

    fun getProblems(user: User, groupId: Long, date: LocalDate): List<ProblemsOfUser> {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }
        if (group.isPrivate && !containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ForbiddenException("해당 비밀 그룹에 속해있지 않습니다.")
        }

        val users = containGroupRepository.findAllByGroup(group).map { it.user }

        val problems = problemRepository.findAllBySolvedDateAndUserIsIn(date, users)

        return mapUserProblem(users, problems)
    }

    fun getProblems(user: User, groupId: Long, yearMonth: YearMonth): List<ProblemsOfUser> {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }
        if (group.isPrivate && !containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ForbiddenException("해당 비밀 그룹에 속해있지 않습니다.")
        }

        val users = containGroupRepository.findAllByGroup(group).map { it.user }
        val start = yearMonth.atDay(1)
        val end = yearMonth.atEndOfMonth()

        val problems = problemRepository.findAllBySolvedDateBetweenAndUserIsIn(start, end, users)

        return mapUserProblem(users, problems)
    }

    fun getProblems(user: User, groupId: Long, startDate: LocalDate, endDate: LocalDate): List<ProblemsOfUser> {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }
        if (group.isPrivate && !containGroupRepository.existsByGroupAndUser(group, user)) {
            throw ForbiddenException("해당 비밀 그룹에 속해있지 않습니다.")
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