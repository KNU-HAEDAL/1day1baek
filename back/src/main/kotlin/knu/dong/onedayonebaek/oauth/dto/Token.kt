package knu.dong.onedayonebaek.oauth.dto

import io.swagger.v3.oas.annotations.media.Schema


data class Token(
    @Schema(description = "Access Token", nullable = false)
    val accessToken: String,

    @Schema(description = "Refresh Token", nullable = false)
    val refreshToken: String
)