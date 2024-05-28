package knu.dong.onedayonebaek.common.exception.response

import io.swagger.v3.oas.annotations.media.Schema

data class UnauthorizedResponse(
    @Schema(description = "error code", nullable = false, example = "unauthorized")
    override val code: String,

    @Schema(description = "error message", nullable = false, example = "unauthorized error")
    override val message: String
): ErrorResponse
