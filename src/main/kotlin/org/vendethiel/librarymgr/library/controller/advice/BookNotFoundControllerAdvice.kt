package org.vendethiel.librarymgr.library.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.vendethiel.librarymgr.library.exception.BookNotFoundException

@RestControllerAdvice
class BookNotFoundControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun bookNotFoundHandler(ex: BookNotFoundException): String? {
        return ex.message
    }
}
