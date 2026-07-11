package org.vendethiel.librarymgr.library.exception

import org.vendethiel.librarymgr.library.model.BookId

class BookAlreadyExistsException(id: BookId) : RecordAlreadyExistsException("book", id.id)