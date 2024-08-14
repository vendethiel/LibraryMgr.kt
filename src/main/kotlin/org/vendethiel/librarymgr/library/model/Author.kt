package org.vendethiel.librarymgr.library.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@NamedEntityGraph(
    name = "Author.books",
    attributeNodes = [NamedAttributeNode("books")]
)
data class Author(
    val name: String,

    @ManyToMany
    @JoinTable(
        name = "author_book",
        joinColumns = [JoinColumn(name = "author_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )

    @JsonIgnoreProperties("authors")
    val books: List<Book> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)