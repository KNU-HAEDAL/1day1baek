package knu.dong.onedayonebaek.common.exception

class ForbiddenException(
    val code: String = "forbidden",
    message: String = "권한이 없습니다."
): RuntimeException(message)