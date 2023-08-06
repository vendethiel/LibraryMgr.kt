package org.vendethiel.librarymgr.library.exception

class AuthorNotFoundException(id: Long) : RecordNotFoundException("author", id)