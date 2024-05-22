package knu.dong.onedayonebaek.repository

import knu.dong.onedayonebaek.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {

    fun findByLoginId(loginId: String): User
    fun existsByLoginId(loginId: String): Boolean
}