package org.vendethiel.librarymgr.library.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.vendethiel.librarymgr.library.exception.AuthorNotFoundException

@RestControllerAdvice
class AuthorNotFoundControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(AuthorNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun authorNotFoundHandler(ex: AuthorNotFoundException): String? {
        return ex.message
    }
}
