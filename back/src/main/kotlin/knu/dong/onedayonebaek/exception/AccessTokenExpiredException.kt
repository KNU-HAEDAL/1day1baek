package knu.dong.onedayonebaek.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Access token expired")
class AccessTokenExpiredException(): RuntimeException()