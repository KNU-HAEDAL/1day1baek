package knu.dong.onedayonebaek.user.service

import knu.dong.onedayonebaek.user.domain.User
import knu.dong.onedayonebaek.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun findUserByLoginId(loginId: String): Optional<User> = userRepository.findByLoginId(loginId)
}