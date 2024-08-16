package org.vendethiel.librarymgr.library.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.vendethiel.librarymgr.library.model.Author
import org.vendethiel.librarymgr.library.service.AuthorService

@RestController
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {
    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun list(): Iterable<Author> =
        service.list()

    @GetMapping("/search", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun search(@RequestParam name: String): Iterable<Author> {
        return listOf()
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun get(@PathVariable id: Long): Author? =
        service.find(id)

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun create(@RequestBody authorData: Author): Author =
        service.create(authorData)
}