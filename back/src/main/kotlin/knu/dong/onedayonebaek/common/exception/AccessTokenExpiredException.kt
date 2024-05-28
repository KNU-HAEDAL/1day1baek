package knu.dong.onedayonebaek.common.exception

class AccessTokenExpiredException(
    val code: String = "access_token_expired",
    message: String = "Access token expired"
): RuntimeException(message)