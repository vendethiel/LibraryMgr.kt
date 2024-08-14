package org.vendethiel.librarymgr.library.exception

class AuthorAlreadyExistsException(id: Long) : RecordAlreadyExistsException("book", id)