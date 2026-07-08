package org.vendethiel.librarymgr.library.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*

// XXX consider making all fields nullable so that they're usable for search-by-example?
@Entity
@NamedEntityGraph(
    name = "Book.authors",
    attributeNodes = [NamedAttributeNode("authors")]
)
class Book(
    var title: String,
    var description: String,
    @Column(unique=true)
    var isbn: String,

    @ManyToMany(mappedBy = "books")
    @JsonIgnoreProperties("books")
    var authors: MutableList<Author> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)