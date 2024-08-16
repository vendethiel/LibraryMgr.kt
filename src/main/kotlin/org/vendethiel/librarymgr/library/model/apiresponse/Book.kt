package org.vendethiel.librarymgr.library.model.apiresponse
import org.vendethiel.librarymgr.library.model.Book as BookModel

data class Book(val title: String, val description: String, val isbn: String) {
    fun toModel(): BookModel =
        BookModel(title, description, isbn)
}