package knu.dong.onedayonebaek.swagger

import io.swagger.v3.oas.annotations.media.Schema

data class RefreshTokenExpiredResponse(
    @Schema(description = "error code", nullable = false, example = "refresh_token_expired")
    val code: String,

    @Schema(description = "error message", nullable = false, example = "Refresh token expired")
    val message: String
)