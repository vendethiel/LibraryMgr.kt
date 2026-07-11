package org.vendethiel.librarymgr.library.inputs

import org.vendethiel.librarymgr.library.model.Book

data class BookInput(val title: String, val description: String, val isbn: String) {
    fun toModel(): Book = Book(title, description, isbn)
}