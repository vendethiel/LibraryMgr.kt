package org.vendethiel.librarymgr.library.result

import org.vendethiel.librarymgr.library.exception.ResultSerializationError
import org.vendethiel.librarymgr.library.model.Author

data class AuthorWithBooksResult(val id: Long, val name: String, val viaf: String, val books: List<BookResult>) {
    companion object {
        fun fromModel(author: Author): AuthorWithBooksResult {
            val id = author.id ?: throw ResultSerializationError(Author::class, "id")
            val books = author.books.map { BookResult.fromModel(it) }
            return AuthorWithBooksResult(id, author.name, author.viaf, books)
        }
    }
}
