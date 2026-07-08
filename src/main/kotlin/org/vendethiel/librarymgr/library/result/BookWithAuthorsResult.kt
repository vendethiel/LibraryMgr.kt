package org.vendethiel.librarymgr.library.result

import org.vendethiel.librarymgr.library.exception.ResultSerializationError
import org.vendethiel.librarymgr.library.model.Book

data class BookWithAuthorsResult(val id: Long, val title: String, val description: String, val isbn: String, val authors: List<AuthorResult>) {
    companion object {
        fun fromModel(book: Book): BookWithAuthorsResult {
            val id = book.id ?: throw ResultSerializationError(Book::class, "id")
            val authors = book.authors.map { AuthorResult.fromModel(it) }
            return BookWithAuthorsResult(id, book.title, book.description, book.isbn, authors)
        }
    }
}