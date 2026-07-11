package org.vendethiel.librarymgr.library.result

import org.vendethiel.librarymgr.library.exception.ResultSerializationError
import org.vendethiel.librarymgr.library.model.Book
import org.vendethiel.librarymgr.library.model.BookId

data class BookResult(val id: BookId, val title: String, val description: String, val isbn: String) {
    companion object {
        fun fromModel(book: Book): BookResult {
            val id = book.id ?: throw ResultSerializationError(Book::class, "id")
            return BookResult(id, book.title, book.description, book.isbn)
        }
    }
}