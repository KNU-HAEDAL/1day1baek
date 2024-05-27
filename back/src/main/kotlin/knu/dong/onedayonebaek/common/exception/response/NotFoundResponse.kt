package knu.dong.onedayonebaek.common.exception.response

import io.swagger.v3.oas.annotations.media.Schema

data class NotFoundResponse(
    @Schema(description = "error code", nullable = false, example = "not_found")
    override val code: String,

    @Schema(description = "error message", nullable = false, example = "Not Found error")
    override val message: String
): ErrorResponse
