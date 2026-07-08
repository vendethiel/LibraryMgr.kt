package org.vendethiel.librarymgr.library.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@NamedEntityGraph(
    name = "Author.books",
    attributeNodes = [NamedAttributeNode("books")]
)
class Author(
    var name: String,
    @Column(unique=true)
    var viaf: String, // Virtual International Authority File

    @ManyToMany
    @JoinTable(
        name = "author_book",
        joinColumns = [JoinColumn(name = "author_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    @JsonIgnoreProperties("authors")
    var books: MutableList<Book> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)