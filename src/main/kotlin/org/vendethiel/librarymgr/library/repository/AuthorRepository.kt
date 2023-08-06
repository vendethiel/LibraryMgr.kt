package org.vendethiel.librarymgr.library.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.vendethiel.librarymgr.library.model.Author
import java.util.*

interface AuthorRepository : PagingAndSortingRepository<Author, Long>, CrudRepository<Author, Long> {
    @EntityGraph("Author.books")
    @Query("select a from Author a join fetch a.books b where a.id = :id")
    fun findByIdWithBooks(id: Long): Optional<Author>

    @EntityGraph("Author.books")
    @Query("select a from Author a join fetch a.books b")
    fun findAllWithBooks(): Iterable<Author>
}