package knu.dong.onedayonebaek.service

import knu.dong.onedayonebaek.domain.ContainGroup
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.*
import knu.dong.onedayonebaek.exception.ForbiddenException
import knu.dong.onedayonebaek.exception.NotFoundException
import knu.dong.onedayonebaek.repository.ContainGroupRepository
import knu.dong.onedayonebaek.repository.GroupRepository
import knu.dong.onedayonebaek.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
@Transactional(readOnly = true)
class GroupService (
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val containGroupRepository: ContainGroupRepository,
    private val passwordEncoder: PasswordEncoder
){
    fun getGroups(): List<GroupOfListDto> =
        groupRepository.findAll()
            .stream()
            .map { it.toGroupOfListDto() }
            .collect(Collectors.toList())

    fun getGroupDetail(user: User, groupId: Long): GroupDetailDto {
        val group = groupRepository.findById(groupId).orElseThrow{ NotFoundException("해당 그룹이 없습니다.") }

        if (!group.isPrivate) {
            return group.toGroupDetailDto()
        }

        if (group.users.any { it.user.id == user.id }) {
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

    private fun getRandomInviteCode(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}