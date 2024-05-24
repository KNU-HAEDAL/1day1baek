package knu.dong.onedayonebaek.config

import knu.dong.onedayonebaek.oauth.OAuth2SuccessHandler
import knu.dong.onedayonebaek.oauth.filter.JwtAuthFilter
import knu.dong.onedayonebaek.oauth.filter.JwtExceptionFilter
import knu.dong.onedayonebaek.oauth.service.CustomOAuth2UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuth2UserService: CustomOAuth2UserService,
    private val successHandler: OAuth2SuccessHandler,
    private val jwtAuthFilter: JwtAuthFilter,
    @Value("\${custom.client.domain}") private val clientDomain: String,
    @Value("\${custom.client.port}") private val clientPort: String,
) {

    private val permitAllPaths = arrayOf(
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v3/api-docs/**",
        "/token/**"
    )


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .cors {}
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(*permitAllPaths).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtExceptionFilter(), OAuth2LoginAuthenticationFilter::class.java)
            .oauth2Login {
                it
                    .loginPage("$clientDomain:$clientPort/login")
                    .successHandler(successHandler)
                    .userInfoEndpoint { i -> i.userService(oAuth2UserService) } }
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOrigin("http://localhost:5173")
        configuration.addAllowedOrigin("http://haedal.iptime.org:5173")
        configuration.addAllowedMethod("*")
        configuration.addAllowedHeader("*")
        configuration.addExposedHeader("*")
        configuration.allowCredentials = true
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}