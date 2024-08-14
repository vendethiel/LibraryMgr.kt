package org.vendethiel.librarymgr.library.service

import org.springframework.stereotype.Service
import org.vendethiel.librarymgr.library.exception.BookAlreadyExistsException
import org.vendethiel.librarymgr.library.exception.BookNotFoundException
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.repository.BookRepository
import java.util.*

@Service
class BookService(private val repository: BookRepository) {
    fun find(id: Long): Book =
        unwrap404(id, repository.findByIdWithAuthors(id))

    fun list(): Iterable<Book> =
        repository.findAllWithAuthors()

    fun create(book: Book): Book {
        book.id?.let { throw BookAlreadyExistsException(it) }
        return repository.save(book)
    }

    private fun unwrap404(id: Long, book: Optional<Book>): Book =
        book.orElseThrow { BookNotFoundException(id) }
}