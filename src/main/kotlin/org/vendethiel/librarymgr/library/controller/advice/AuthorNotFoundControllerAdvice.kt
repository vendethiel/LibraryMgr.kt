package org.vendethiel.librarymgr.library.controller.advice

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.vendethiel.librarymgr.library.exception.AuthorNotFoundException

@Service
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class AuthorNotFoundControllerAdvice : ResponseEntityExceptionHandler() {
    @ResponseBody
    @ExceptionHandler(AuthorNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun authorNotFoundHandler(ex: AuthorNotFoundException): String? {
        return ex.message
    }
}
