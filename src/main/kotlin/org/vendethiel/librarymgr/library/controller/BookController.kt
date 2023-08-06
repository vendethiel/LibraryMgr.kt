package org.vendethiel.librarymgr.library.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.vendethiel.librarymgr.library.exception.BookNotFoundException
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.repository.BookRepository

@Controller
@RequestMapping("/books")
class BookController(private val repository: BookRepository) {
    @GetMapping("", produces = [MediaType.TEXT_HTML_VALUE])
    fun listHTML(model: Model): String {
        model["books"] = repository.findAll()
        return "books/index"
    }

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun listData(): Iterable<Book> {
        return repository.findAllWithAuthors()
    }

    @GetMapping("/{id}", produces = [MediaType.TEXT_HTML_VALUE])
    fun getHTML(@PathVariable id: Long, model: Model): String {
        model["book"] = repository.findByIdWithAuthors(id).orElseThrow { BookNotFoundException(id) }
        return "books/show"
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun getData(@PathVariable id: Long): Book {
        return repository.findByIdWithAuthors(id).orElseThrow { BookNotFoundException(id) }
    }
}