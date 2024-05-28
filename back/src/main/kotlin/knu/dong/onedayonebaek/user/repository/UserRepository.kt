package knu.dong.onedayonebaek.user.repository

import knu.dong.onedayonebaek.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository: JpaRepository<User, Long> {

    fun findByLoginId(loginId: String): Optional<User>
    fun getByLoginId(loginId: String): User
    fun existsByLoginId(loginId: String): Boolean
}