package knu.dong.onedayonebaek.repository

import knu.dong.onedayonebaek.domain.Group
import org.springframework.data.jpa.repository.JpaRepository

interface GroupRepository: JpaRepository<Group, Long> {
}