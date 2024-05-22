package knu.dong.onedayonebaek.oauth.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import knu.dong.onedayonebaek.oauth.dto.Token
import knu.dong.onedayonebaek.oauth.service.TokenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class TokenController(private val tokenService: TokenService) {

    @GetMapping("/token/expired")
    fun auth(): String {
        throw RuntimeException()
    }

    @GetMapping("/token/refresh")
    fun refreshAuth(request: HttpServletRequest, response: HttpServletResponse): Token {
        val token = request.getHeader("Refresh")
        if (token != null && tokenService.verifyToken(token)) {
            val id = tokenService.getUid(token)
            val newToken = tokenService.generateToken(id, "USER")

            response.addHeader("Authorization", newToken.accessToken)
            response.addHeader("Refresh", newToken.refreshToken)

            response.contentType = "application/json;charset=UTF-8"

            return newToken
        }

        throw RuntimeException()
    }
}