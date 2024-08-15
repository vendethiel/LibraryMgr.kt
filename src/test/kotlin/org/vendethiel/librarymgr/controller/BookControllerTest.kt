package org.vendethiel.librarymgr.controller

import org.vendethiel.librarymgr.library.controller.BookController
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.service.BookService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BookController::class)
class BookControllerTest(
    @Autowired private val mvc: MockMvc
) {
    @MockkBean
    lateinit var bookService: BookService

    @Test
    fun listEmpty() {
        every { bookService.list() } returns listOf()

        mvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(0))
    }

    @Test
    fun listGivesABook() {
        val book = Book("Dictionary", "List of words", listOf(), 1)
        every { bookService.list() } returns listOf(book)

        mvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].id").value(1))
            .andExpect(jsonPath("$.[0].title").value("Dictionary"))
            .andExpect(jsonPath("$.[0].description").value("List of words"))
            .andExpect(jsonPath("$.[0].authors").isEmpty)
    }

    @Test
    fun listGivesAllBooks() {
        val books = listOf(
            Book("Dictionary", "List of words", listOf(), 1),
            Book("Encyclopedia", "List of things", listOf(), 2),
            Book("Thesaurus", "List of synonyms", listOf(), 3),
        )
        every { bookService.list() } returns books

        mvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$.[0].title").value("Dictionary"))
            .andExpect(jsonPath("$.[1].title").value("Encyclopedia"))
            .andExpect(jsonPath("$.[2].title").value("Thesaurus"))
    }

    // TODO find
    // TODO find 404
    // TODO test no recursion
    // TODO create
    // TODO delete
}