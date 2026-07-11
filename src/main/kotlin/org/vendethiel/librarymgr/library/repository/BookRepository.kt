package org.vendethiel.librarymgr.library.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.model.BookId
import java.util.*

@Repository
interface BookRepository : PagingAndSortingRepository<Book, BookId>, CrudRepository<Book, BookId> {
    @EntityGraph("Book.authors")
    @Query("select b from Book b join fetch b.authors a where b.id = :id")
    fun findByIdWithAuthors(id: Long): Optional<Book>

    @EntityGraph("Book.authors")
    @Query("select b from Book b join fetch b.authors a")
    fun findAllWithAuthors(): Iterable<Book>

    @EntityGraph("Book.authors")
    @Query("select b from Book b join fetch b.authors a where b.isbn = :isbn")
    fun findByISBNWithAuthors(isbn: String): Book?
}