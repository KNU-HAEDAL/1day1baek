package knu.dong.onedayonebaek.common.exception.response

import io.swagger.v3.oas.annotations.media.Schema

data class ForbiddenResponse(
    @Schema(description = "error code", nullable = false, example = "forbidden")
    override val code: String,

    @Schema(description = "error message", nullable = false, example = "권한이 없습니다.")
    override val message: String
): ErrorResponse
