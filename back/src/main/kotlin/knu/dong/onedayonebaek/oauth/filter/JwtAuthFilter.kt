package knu.dong.onedayonebaek.oauth.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import knu.dong.onedayonebaek.oauth.dto.UserDto
import knu.dong.onedayonebaek.oauth.service.TokenService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean


private val myLogger = KotlinLogging.logger {}

class JwtAuthFilter(private val tokenService: TokenService): GenericFilterBean() {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val authorization = (request as HttpServletRequest).getHeader("Authorization")

        if (authorization != null) {
            if (authorization.startsWith("Bearer")) {
                val token = authorization.split(" ")[1]

                if (tokenService.verifyToken(token)) {
                    val id = tokenService.getUid(token)

                    // TODO 디비 연결 후, 디비 조회하도록 수정 필요
                    val userDto = UserDto(id, "이름", "프로필 url")

                    val auth: Authentication = getAuthentication(userDto)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
            else {
                myLogger.error { "token doesn't start with 'Bearer'" }
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
}