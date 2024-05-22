package knu.dong.onedayonebaek.oauth

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import knu.dong.onedayonebaek.oauth.dto.Token
import knu.dong.onedayonebaek.oauth.dto.UserDto
import knu.dong.onedayonebaek.oauth.service.TokenService
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder


// 놀랍게도 SimpleUrlAuthenticationSuccessHandler에 protected 변수로 logger가 선언되어 myLogger
private val myLogger = KotlinLogging.logger {}

@Component
class OAuth2SuccessHandler(
    @Value("\${custom.client.domain}") private var clientDomain: String,
    @Value("\${custom.client.port}") private var clientPort: String,
    private val tokenService: TokenService
): SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as OAuth2User

        val attributes = oAuth2User.attributes
        val userDto = UserDto(
            attributes["id"] as String,
            attributes["name"] as String,
            attributes["profileUrl"] as String
        )

        // TODO DB 연결 후 최초 로그인이라면 회원가입 처리

        myLogger.info{ "토큰 발행 시작" }

        val token: Token = tokenService.generateToken(userDto.id, "USER")

        response.contentType = "text/html;charset=UTF-8"
        response.contentType = "application/json;charset=UTF-8"
        response.addHeader("Authorization", token.accessToken)
        response.addHeader("Refresh", token.refreshToken)

        val targetUrl = UriComponentsBuilder
            .fromUriString("$clientDomain:$clientPort/")
            .build().toUriString();

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}