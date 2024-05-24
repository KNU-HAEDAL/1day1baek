package knu.dong.onedayonebaek.repository

import knu.dong.onedayonebaek.domain.ContainGroup
import org.springframework.data.jpa.repository.JpaRepository

interface ContainGroupRepository: JpaRepository<ContainGroup, Long> {
}