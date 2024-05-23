package knu.dong.onedayonebaek.exception

class AccessTokenExpiredException(message: String = "Access token expired"): RuntimeException(message)