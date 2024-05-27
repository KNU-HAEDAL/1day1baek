package knu.dong.onedayonebaek.common.exception

class AccessTokenExpiredException(message: String = "Access token expired"): RuntimeException(message)