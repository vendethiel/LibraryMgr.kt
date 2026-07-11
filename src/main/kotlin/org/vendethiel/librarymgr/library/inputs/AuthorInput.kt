package org.vendethiel.librarymgr.library.inputs

import org.vendethiel.librarymgr.library.model.Author

data class AuthorInput(val name: String, val viaf: String) {
    fun toModel(): Author = Author(name, viaf)
}
