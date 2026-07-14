package org.vendethiel.librarymgr.library.result

import org.vendethiel.librarymgr.library.exception.ResultSerializationError
import org.vendethiel.librarymgr.library.model.Author

data class AuthorResult(val id: Long, val name: String, val viaf: String) {
    companion object {
        fun fromModel(author: Author): AuthorResult {
            val id = author.id ?: throw ResultSerializationError(Author::class, "id")
            return AuthorResult(id, author.name, author.viaf ?: "") // TODO make viaf non-null in DB
        }
    }
}
