package knu.dong.onedayonebaek.exception

import knu.dong.onedayonebaek.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AccessTokenExpiredException::class)
    fun handleAccessTokenExpiredException(e: AccessTokenExpiredException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("access_token_expired", e.message!!)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(RefreshTokenExpiredException::class)
    fun handleRefreshTokenExpiredException(e: RefreshTokenExpiredException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse("refresh_token_expired", e.message!!)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }
}