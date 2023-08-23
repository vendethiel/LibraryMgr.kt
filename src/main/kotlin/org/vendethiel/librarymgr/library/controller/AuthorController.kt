package org.vendethiel.librarymgr.library.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.vendethiel.librarymgr.library.model.Author
import org.vendethiel.librarymgr.library.service.AuthorService

@RestController
@RequestMapping("/authors")
class AuthorController(private val service: AuthorService) {
    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun listData(): Iterable<Author> {
        return service.list()
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    fun getData(@PathVariable id: Long): Author? {
        return service.find(id)
    }
}