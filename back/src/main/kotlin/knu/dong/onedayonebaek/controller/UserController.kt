package knu.dong.onedayonebaek.controller

import knu.dong.onedayonebaek.oauth.dto.UserDto
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @GetMapping
    fun loginUserInfo(authentication: Authentication) = authentication.principal as UserDto
}