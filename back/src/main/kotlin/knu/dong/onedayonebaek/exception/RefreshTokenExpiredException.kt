package knu.dong.onedayonebaek.exception

class RefreshTokenExpiredException(message: String = "Refresh token expired"): RuntimeException(message)