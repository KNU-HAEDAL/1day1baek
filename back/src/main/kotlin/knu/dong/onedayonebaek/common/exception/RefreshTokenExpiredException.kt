package knu.dong.onedayonebaek.common.exception

class RefreshTokenExpiredException(message: String = "Refresh token expired"): RuntimeException(message)