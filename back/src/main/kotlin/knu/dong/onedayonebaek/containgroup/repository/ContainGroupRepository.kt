package knu.dong.onedayonebaek.containgroup.repository

import knu.dong.onedayonebaek.containgroup.domain.ContainGroup
import knu.dong.onedayonebaek.group.domain.Group
import knu.dong.onedayonebaek.user.domain.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ContainGroupRepository: JpaRepository<ContainGroup, Long> {

    fun findByGroupAndUser(group: Group, user: User): Optional<ContainGroup>
    fun existsByGroupAndUser(group: Group, user: User): Boolean

    @EntityGraph(attributePaths = ["user"])
    fun findAllByGroup(group: Group): List<ContainGroup>
}