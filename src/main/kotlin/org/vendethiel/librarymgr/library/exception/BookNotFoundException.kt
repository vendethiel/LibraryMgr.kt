package org.vendethiel.librarymgr.library.exception

class BookNotFoundException(id: Long) : RecordNotFoundException("book", id)