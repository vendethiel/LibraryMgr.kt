package org.vendethiel.librarymgr.library.exception

class BookAlreadyExistsException(id: Long) : RecordAlreadyExistsException("book", id)