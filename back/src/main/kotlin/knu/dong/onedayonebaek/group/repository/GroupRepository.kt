package knu.dong.onedayonebaek.group.repository

import knu.dong.onedayonebaek.group.domain.Group
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface GroupRepository: JpaRepository<Group, Long> {

    fun findByInviteCode(inviteCode: String): Optional<Group>
}