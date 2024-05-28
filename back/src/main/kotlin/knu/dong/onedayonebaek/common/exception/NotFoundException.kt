package knu.dong.onedayonebaek.common.exception

class NotFoundException(
    val code: String = "not_found",
    message: String = "Not Found"
): RuntimeException(message)