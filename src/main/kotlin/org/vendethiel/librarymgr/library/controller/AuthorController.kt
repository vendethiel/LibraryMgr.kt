package org.vendethiel.librarymgr.library.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.vendethiel.librarymgr.library.inputs.AuthorInput
import org.vendethiel.librarymgr.library.model.Author
import org.vendethiel.librarymgr.library.result.AuthorResult
import org.vendethiel.librarymgr.library.result.AuthorWithBooksResult
import org.vendethiel.librarymgr.library.service.AuthorService

@RestController
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {
    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun list(): Iterable<AuthorWithBooksResult> =
        service.list().map { AuthorWithBooksResult.fromModel(it) }

    @GetMapping("/search", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun search(@RequestParam name: String): Iterable<AuthorWithBooksResult> =
        service.filterByName(name).map { AuthorWithBooksResult.fromModel(it) }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun get(@PathVariable id: Long): AuthorWithBooksResult? =
        service.find(id).let { AuthorWithBooksResult.fromModel(it) }

    @PostMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun create(@RequestBody authorData: AuthorInput): AuthorResult =
        AuthorResult.fromModel(service.create(authorData.toModel()))
}