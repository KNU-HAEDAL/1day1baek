package knu.dong.onedayonebaek.user.controller

import knu.dong.onedayonebaek.user.domain.User
import knu.dong.onedayonebaek.user.dto.toUserDto
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping
    fun loginUserInfo(authentication: Authentication) = (authentication.principal as User).toUserDto()
}