package knu.dong.onedayonebaek.exception.response

import io.swagger.v3.oas.annotations.media.Schema

data class BadRequestResponse(
    @Schema(description = "error code", nullable = false, example = "bad_request")
    override val code: String,

    @Schema(description = "error message", nullable = false, example = "bad request error")
    override val message: String
): ErrorResponse
