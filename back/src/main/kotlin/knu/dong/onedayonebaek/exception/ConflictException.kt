package knu.dong.onedayonebaek.exception

class ConflictException(
    val code: String = "conflict",
    message: String = "Conflict"
): RuntimeException(message)