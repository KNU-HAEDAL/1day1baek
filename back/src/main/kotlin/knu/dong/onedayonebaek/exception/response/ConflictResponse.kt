package knu.dong.onedayonebaek.exception.response

import io.swagger.v3.oas.annotations.media.Schema

data class ConflictResponse(
    @Schema(description = "error code", nullable = false, example = "conflict")
    override val code: String,

    @Schema(description = "error message", nullable = false, example = "conflict error")
    override val message: String
): ErrorResponse
