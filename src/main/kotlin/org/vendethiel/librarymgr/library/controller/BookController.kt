package org.vendethiel.librarymgr.library.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.vendethiel.librarymgr.library.inputs.BookInput
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.result.BookResult
import org.vendethiel.librarymgr.library.result.BookWithAuthorsResult
import org.vendethiel.librarymgr.library.service.BookService

@RestController
@RequestMapping("/books")
class BookController(private val service: BookService) {

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun list(): Iterable<BookWithAuthorsResult> =
        service.list().map { BookWithAuthorsResult.fromModel(it) }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun get(@PathVariable id: Long): BookWithAuthorsResult? =
        service.find(id).let { BookWithAuthorsResult.fromModel(it) }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun create(@RequestBody bookData: BookInput): BookResult =
        BookResult.fromModel(service.create(bookData.toModel()))

    // TODO live() method that returns a Reactor
}