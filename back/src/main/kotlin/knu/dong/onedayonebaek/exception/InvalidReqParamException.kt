package knu.dong.onedayonebaek.exception

class InvalidReqParamException(message: String = "Refresh token expired"): RuntimeException(message)