package org.vendethiel.librarymgr.library.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.vendethiel.librarymgr.library.exception.ResultSerializationError

@RestControllerAdvice
class ResultSerializationErrorControllerAdvice {
    @ExceptionHandler(ResultSerializationError::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun resultSerializationErrorHandler(ex: ResultSerializationError): String? {
        return ex.message
    }
}