package knu.dong.onedayonebaek.oauth.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import knu.dong.onedayonebaek.common.exception.AccessTokenExpiredException
import knu.dong.onedayonebaek.common.exception.response.BaseErrorResponse
import org.springframework.web.filter.OncePerRequestFilter


class JwtExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: AccessTokenExpiredException) {
            val objectMapper = ObjectMapper()

            val error = BaseErrorResponse(e.code, e.message!!)

            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json;charset=UTF-8"
            response.writer.write(objectMapper.writeValueAsString(error))
        }
    }
}