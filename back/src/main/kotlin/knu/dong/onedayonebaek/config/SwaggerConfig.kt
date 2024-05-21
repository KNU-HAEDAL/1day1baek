package knu.dong.onedayonebaek.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val jwt = "JWT"
        val securityRequirement = SecurityRequirement().addList(jwt)
        val components = Components().addSecuritySchemes(jwt, SecurityScheme()
            .name(jwt)
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
        )

        return OpenAPI()
            .components(Components())
            .info(apiInfo())
            .addSecurityItem(securityRequirement)
            .components(components)
    }

    private fun apiInfo() = Info()
        .title("1일 1백준 APIs")
        .description("1일 1백준 서비스에 대한 APIs")
        .version("1.0.0")
}