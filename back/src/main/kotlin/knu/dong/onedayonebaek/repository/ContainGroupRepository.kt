package knu.dong.onedayonebaek.repository

import knu.dong.onedayonebaek.domain.ContainGroup
import knu.dong.onedayonebaek.domain.Group
import knu.dong.onedayonebaek.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ContainGroupRepository: JpaRepository<ContainGroup, Long> {

    fun findByGroupAndUser(group: Group, user: User): Optional<ContainGroup>
    fun existsByGroupAndUser(group: Group, user: User): Boolean
}