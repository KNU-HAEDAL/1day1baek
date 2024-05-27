package knu.dong.onedayonebaek.common.exception

class InvalidReqParamException(message: String = "Refresh token expired"): RuntimeException(message)