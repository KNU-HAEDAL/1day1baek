package knu.dong.onedayonebaek.service

import knu.dong.onedayonebaek.dto.GroupInfo
import knu.dong.onedayonebaek.dto.toGroupInfo
import knu.dong.onedayonebaek.repository.ContainGroupRepository
import knu.dong.onedayonebaek.repository.GroupRepository
import knu.dong.onedayonebaek.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class GroupService (
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val containGroupRepository: ContainGroupRepository
){
    fun getPublicGroups(): List<GroupInfo> =
        groupRepository.findAllByIsPrivate(false)
            .stream()
            .map { it.toGroupInfo() }
            .collect(Collectors.toList())
}