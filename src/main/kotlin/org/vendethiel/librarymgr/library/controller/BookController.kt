package org.vendethiel.librarymgr.library.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.service.BookService

@RestController
@RequestMapping("/books")
class BookController(private val service: BookService) {

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun list(): Iterable<Book> {
        return service.list()
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun get(@PathVariable id: Long): Book {
        return service.find(id)
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun create(@RequestBody bookData: Book): Book {
        return service.create(bookData)
    }
}