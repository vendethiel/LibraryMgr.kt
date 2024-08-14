package org.vendethiel.librarymgr.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.vendethiel.librarymgr.library.controller.AuthorController
import org.vendethiel.librarymgr.library.exception.AuthorNotFoundException
import org.vendethiel.librarymgr.library.model.Author
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.service.AuthorService

@WebMvcTest(AuthorController::class)
class AuthorControllerTest(
    @Autowired private val mvc: MockMvc
) {
    @MockkBean
    lateinit var authorService: AuthorService

    @Test
    fun listGivesAnAuthor() {
        val author = Author("Lee J", listOf(), 1)
        every { authorService.list() } returns listOf(author)

        mvc.perform(get("/authors"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].id").value(1))
            .andExpect(jsonPath("$.[0].name").value("Lee J"))
            .andExpect(jsonPath("$.[0].books").isEmpty)
    }

    @Test
    fun listGivesAllAuthors() {
        val authors = listOf(
            Author("Lee J", listOf(), 1),
            Author("Billie J", listOf(), 2),
            Author("Foolish", listOf(), 5),
        )
        every { authorService.list() } returns authors

        mvc.perform(get("/authors"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(3))
            .andExpect(jsonPath("$.[0].name").value("Lee J"))
            .andExpect(jsonPath("$.[1].name").value("Billie J"))
            .andExpect(jsonPath("$.[2].name").value("Foolish"))
    }

    @Test
    fun findAuthorById() {
        val author = Author("Lee J", listOf(), 1)
        every { authorService.find(1) } returns author

        mvc.perform(get("/authors/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Lee J"))
            .andExpect(jsonPath("$.books").isEmpty)
    }

    @Test
    fun authorIncludesBooks() {
        val books = listOf(
            Book("Sailing Away", "Come Sail Away"),
            Book("Long Gone", "Far Gone"),
        )
        val author = Author("Lee J", books, 1)
        every { authorService.find(1) } returns author

        mvc.perform(get("/authors/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Lee J"))
            .andExpect(jsonPath("$.books.length()").value(2))
            .andExpect(jsonPath("$.books[0].title").value("Sailing Away"))
            .andExpect(jsonPath("$.books[0].description").value("Come Sail Away"))
            .andExpect(jsonPath("$.books[1].title").value("Long Gone"))
            .andExpect(jsonPath("$.books[1].description").value("Far Gone"))
            // No recursive serialization...
            .andExpect(jsonPath("$.books[*].authors").isEmpty)
    }

    @Test
    fun nonExistingAuthor404s() {
        every { authorService.find(any()) } throws AuthorNotFoundException(0)

        mvc.perform(get("/authors/123"))
            .andExpect(status().isNotFound)
    }

    // TODO test create
    // TODO test create already exists
}