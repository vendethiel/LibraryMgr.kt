package org.vendethiel.librarymgr.library.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

@Entity
@NamedEntityGraph(
    name = "Book.authors",
    attributeNodes = [NamedAttributeNode("authors")]
)
data class Book(
    var title: String,

    var description: String,

    @ManyToMany(mappedBy = "books")
    @JsonIgnoreProperties("books")
    val authors: List<Author> = listOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)