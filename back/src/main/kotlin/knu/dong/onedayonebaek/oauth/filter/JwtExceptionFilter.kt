package knu.dong.onedayonebaek.oauth.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter


class JwtExceptionFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        response.characterEncoding = "utf-8"

        try {
            filterChain.doFilter(request, response)
        } catch (e: Error) {
            val objectMapper = ObjectMapper()

            val map: MutableMap<String, String> = HashMap()
            map["errortype"] = "Forbidden"
            map["code"] = "403"
            map["message"] = "잘못된 토큰입니다."

            response.writer.write(objectMapper.writeValueAsString(map))
        }
    }
}