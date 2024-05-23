package knu.dong.onedayonebaek.dto

import io.swagger.v3.oas.annotations.media.Schema

data class ErrorResponse(
    @Schema(description = "error code", nullable = false)
    val code: String,

    @Schema(description = "error message", nullable = false, example = "TEST")
    val message: String
)
