package knu.dong.onedayonebaek.oauth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import knu.dong.onedayonebaek.common.exception.RefreshTokenExpiredException
import knu.dong.onedayonebaek.common.exception.response.RefreshTokenExpiredResponse
import knu.dong.onedayonebaek.oauth.dto.Token
import knu.dong.onedayonebaek.oauth.service.TokenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/token")
@Tag(name = "Token APIs", description = "JWT 토큰과 관련된 APIs")
class TokenController(private val tokenService: TokenService) {

    @GetMapping("/refresh")
    @Operation(summary = "Access Token을 재발급받는다.", description = "Refresh Token을 사용해 Access Token을 재발급 받는다.")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Access Token 및 Refresh Token 재발급",
            content = [Content(schema = Schema(implementation = Token::class))]
        ),
        ApiResponse(
            responseCode = "401",
            description = "Refresh Token이 만료된 경우",
            content = [Content(schema = Schema(implementation = RefreshTokenExpiredResponse::class))],
        )
    )

    fun refreshAuth(request: HttpServletRequest, response: HttpServletResponse): Token {
        val token = request.getHeader("Refresh").let {
            if (it == null) {
                null
            }
            else if (it.startsWith("Bearer")) {
                it.split(" ")[1]
            }
            else {
                it
            }
        }

        if (token != null && tokenService.verifyToken(token)) {
            val id = tokenService.getUid(token)
            val newToken = tokenService.generateToken(id, "USER")

            response.addHeader("Authorization", newToken.accessToken)
            response.addHeader("Refresh", newToken.refreshToken)

            response.contentType = "application/json;charset=UTF-8"

            return newToken
        }

        throw RefreshTokenExpiredException()
    }
}