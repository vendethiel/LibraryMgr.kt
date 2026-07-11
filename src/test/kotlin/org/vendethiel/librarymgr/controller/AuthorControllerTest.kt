package org.vendethiel.librarymgr.controller

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.vendethiel.librarymgr.library.controller.AuthorController
import org.vendethiel.librarymgr.library.exception.AuthorNotFoundException
import org.vendethiel.librarymgr.library.inputs.AuthorInput
import org.vendethiel.librarymgr.library.model.Author
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.model.BookId
import org.vendethiel.librarymgr.library.service.AuthorService
import tools.jackson.databind.json.JsonMapper
import kotlin.test.assertEquals

@WebMvcTest(AuthorController::class)
class AuthorControllerTest(
    @Autowired private val mvc: MockMvc,
    @Autowired private val jsonMapper: JsonMapper
) {
    @MockkBean
    lateinit var authorService: AuthorService

    @Test
    fun listEmpty() {
        every { authorService.list() } returns listOf()

        mvc.perform(get("/authors"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(0))
    }

    @Test
    fun listGivesAnAuthor() {
        val author = Author("Lee J", "1", mutableListOf(), 1)
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
            Author("Lee J", "1", mutableListOf(), 1),
            Author("Billie J", "2", mutableListOf(), 2),
            Author("Foolish", "3", mutableListOf(), 5),
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
        val author = Author("Lee J", "1", mutableListOf(), 1)
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
        val books = mutableListOf(
            Book("Sailing Away", "Come Sail Away", "1", mutableListOf(), BookId(1)),
            Book("Long Gone", "Far Gone", "2", mutableListOf(), BookId(2)),
        )
        val author = Author("Lee J", "1", books, 1)
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
        every { authorService.find(123) } throws AuthorNotFoundException(0)

        mvc.perform(get("/authors/123"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun searchByName() {
        val authors = mutableListOf(
            Author("Lee J", "1", mutableListOf(), 1)
        )
        every { authorService.filterByName("ee") } returns authors

        mvc.perform(get("/authors/search").queryParam("name", "ee"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$.[0].id").value(1))
            .andExpect(jsonPath("$.[0].name").value("Lee J"))
            .andExpect(jsonPath("$.[0].viaf").value("1"))
            .andExpect(jsonPath("$.[0].books.length()").value(0))
    }

    @Test
    fun create() {
        val input = AuthorInput("Lee J", "1")
        val slot = slot<Author>()
        every { authorService.create(capture(slot)) } returns Author("Lee J", "1", mutableListOf(), 1)

        mvc.perform(
            post("/authors")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMapper.writeValueAsString(input))
        ).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").value("Lee J"))
            .andExpect(jsonPath("$.viaf").value("1"))
            .andExpect(jsonPath("$.books").doesNotExist())
            .andExpect(jsonPath("$.id").isNumber)

        assertEquals(slot.captured.name, input.name)
        assertEquals(slot.captured.viaf, input.viaf)
    }

    // TODO update
}
