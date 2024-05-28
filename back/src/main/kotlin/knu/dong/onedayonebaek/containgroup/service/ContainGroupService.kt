package knu.dong.onedayonebaek.containgroup.service

import knu.dong.onedayonebaek.containgroup.repository.ContainGroupRepository
import knu.dong.onedayonebaek.group.domain.Group
import knu.dong.onedayonebaek.user.domain.User
import org.springframework.stereotype.Service

@Service
class ContainGroupService(
    private val containGroupRepository: ContainGroupRepository
) {

    fun isMemberOfGroup(group: Group, user: User): Boolean =
        containGroupRepository.existsByGroupAndUser(group, user)
}