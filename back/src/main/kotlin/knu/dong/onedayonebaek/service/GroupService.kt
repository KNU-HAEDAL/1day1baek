package knu.dong.onedayonebaek.service

import knu.dong.onedayonebaek.domain.ContainGroup
import knu.dong.onedayonebaek.domain.User
import knu.dong.onedayonebaek.dto.*
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
    fun getPublicGroups(): List<GroupInfo> =
        groupRepository.findAllByIsPrivate(false)
            .stream()
            .map { it.toGroupInfo() }
            .collect(Collectors.toList())

    @Transactional
    fun createGroup(user: User, req: CreateGroupRequest): CreateGroupResponse {
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

        return group.toCreateGroupResponse()
    }

    private fun getRandomInviteCode(length: Int): String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}