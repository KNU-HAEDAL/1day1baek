package knu.dong.onedayonebaek.oauth.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import knu.dong.onedayonebaek.common.exception.AccessTokenExpiredException
import knu.dong.onedayonebaek.common.exception.NotFoundException
import knu.dong.onedayonebaek.oauth.service.TokenService
import knu.dong.onedayonebaek.user.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean


@Component
class JwtAuthFilter(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
): GenericFilterBean() {
    private val EXCLUDE_URLS: List<String> = listOf(
        "/swagger-ui",
        "/swagger-resources",
        "/v3/api-docs",
        "/token",
        "/github/webhook"
    )

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        if (!shouldExclude(request as HttpServletRequest)) {
            val accessToken = request.getHeader("Authorization").let {
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

            if (accessToken != null && tokenService.verifyToken(accessToken)) {
                val loginId = tokenService.getUid(accessToken)

                val user = userRepository.findByLoginId(loginId).orElseThrow { NotFoundException() }

                SecurityContextHolder.getContext().authentication =
                    UsernamePasswordAuthenticationToken(
                        user, "", listOf(SimpleGrantedAuthority("ROLE_USER"))
                    )
            }
            else {
                throw AccessTokenExpiredException()
            }
        }

        chain.doFilter(request, response)
    }

    private fun shouldExclude(request: HttpServletRequest): Boolean {
        return EXCLUDE_URLS.stream().anyMatch { url: String -> request.requestURI.startsWith(url) }
    }
}