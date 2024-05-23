package knu.dong.onedayonebaek.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import knu.dong.onedayonebaek.exception.response.BadRequestResponse
import knu.dong.onedayonebaek.exception.response.BaseErrorResponse
import knu.dong.onedayonebaek.exception.response.ErrorResponse
import knu.dong.onedayonebaek.exception.response.RefreshTokenExpiredResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


private val myLogger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AccessTokenExpiredException::class)
    fun handleAccessTokenExpiredException(e: AccessTokenExpiredException): ResponseEntity<ErrorResponse> {
        val errorResponse = BaseErrorResponse("access_token_expired", e.message!!)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(RefreshTokenExpiredException::class)
    fun handleRefreshTokenExpiredException(e: RefreshTokenExpiredException): ResponseEntity<ErrorResponse> {
        val errorResponse = RefreshTokenExpiredResponse("refresh_token_expired", e.message!!)

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val fieldErrors = e.bindingResult.fieldErrors
        val fieldError = fieldErrors[fieldErrors.size - 1]

        val fieldName = fieldError.field
        val rejectedValue = fieldError.rejectedValue

        val message = "$fieldName 필드의 입력 값 ${rejectedValue}는 유효하지 않습니다."
        val errorResponse = BadRequestResponse("bad_request", message)

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}