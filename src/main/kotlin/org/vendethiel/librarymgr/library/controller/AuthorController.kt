package org.vendethiel.librarymgr.library.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.vendethiel.librarymgr.library.exception.AuthorNotFoundException
import org.vendethiel.librarymgr.library.model.Author
import org.vendethiel.librarymgr.library.repository.AuthorRepository

@Controller
@RequestMapping("/authors")
class AuthorController(private val repository: AuthorRepository) {
    @GetMapping("", produces = [MediaType.TEXT_HTML_VALUE])
    fun listHTML(model: Model): String {
        model["authors"] = repository.findAll()
        return "authors/index"
    }

    @GetMapping("", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun listData(): Iterable<Author> {
        return repository.findAllWithBooks()
    }

    @GetMapping("/{id}", produces = [MediaType.TEXT_HTML_VALUE])
    fun getHTML(@PathVariable id: Long, model: Model): String {
        model["author"] = repository.findByIdWithBooks(id).orElseThrow { AuthorNotFoundException(id) }
        return "authors/show"
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
    @ResponseBody
    fun getData(@PathVariable id: Long): Author? {
        return repository.findByIdWithBooks(id).orElseThrow { AuthorNotFoundException(id) }
    }
}