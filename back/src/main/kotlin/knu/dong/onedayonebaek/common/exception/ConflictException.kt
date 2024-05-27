package knu.dong.onedayonebaek.common.exception

class ConflictException(
    val code: String = "conflict",
    message: String = "Conflict"
): RuntimeException(message)