package com.alad1nks.dubovozki.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleCustomException(ex: RuntimeException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(
            message = ex.message ?: "Unknown error",
            details = request.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorDetails = ErrorResponse(
            message = ex.message ?: "An error occurred",
            details = request.getDescription(false)
        )
        return ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

data class ErrorResponse(
    val message: String,
    val details: String
)
