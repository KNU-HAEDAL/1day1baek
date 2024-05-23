package knu.dong.onedayonebaek.oauth.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import knu.dong.onedayonebaek.dto.UserDto
import knu.dong.onedayonebaek.dto.toUserDto
import knu.dong.onedayonebaek.exception.AccessTokenExpiredException
import knu.dong.onedayonebaek.oauth.service.TokenService
import knu.dong.onedayonebaek.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean


private val myLogger = KotlinLogging.logger {}

@Component
class JwtAuthFilter(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
): GenericFilterBean() {
    private val EXCLUDE_URLS: List<String> = listOf(
        "/swagger-ui",
        "/swagger-resources",
        "/v3/api-docs",
        "/token"
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

            if (accessToken != null) {
                if (tokenService.verifyToken(accessToken)) {
                    val loginId = tokenService.getUid(accessToken)

                    val user = userRepository.findByLoginId(loginId)
                    val userDto = user.toUserDto()

                    val auth: Authentication = getAuthentication(userDto)
                    SecurityContextHolder.getContext().authentication = auth
                }
                else {
                    throw AccessTokenExpiredException()
                }
            }
        }

        chain.doFilter(request, response)
    }

    private fun getAuthentication(member: UserDto): Authentication {
        return UsernamePasswordAuthenticationToken(
            member, "",
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        )
    }

    private fun shouldExclude(request: HttpServletRequest): Boolean {
        return EXCLUDE_URLS.stream().anyMatch { url: String -> request.requestURI.startsWith(url) }
    }
}