package knu.dong.onedayonebaek.config

import knu.dong.onedayonebaek.oauth.OAuth2SuccessHandler
import knu.dong.onedayonebaek.oauth.filter.JwtAuthFilter
import knu.dong.onedayonebaek.oauth.filter.JwtExceptionFilter
import knu.dong.onedayonebaek.oauth.service.CustomOAuth2UserService
import knu.dong.onedayonebaek.oauth.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuth2UserService: CustomOAuth2UserService,
    private val successHandler: OAuth2SuccessHandler,
    private val tokenService: TokenService
) {


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/login/**",
                        "/"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtExceptionFilter(), OAuth2LoginAuthenticationFilter::class.java)
            .oauth2Login { it.successHandler(successHandler).userInfoEndpoint { i -> i.userService(oAuth2UserService) } }
            .addFilterBefore(JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter::class.java)
            .build()
}