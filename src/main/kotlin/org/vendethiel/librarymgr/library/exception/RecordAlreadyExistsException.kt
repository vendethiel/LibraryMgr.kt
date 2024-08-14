package org.vendethiel.librarymgr.library.exception

abstract class RecordAlreadyExistsException(record: String, id: Long) : RuntimeException("$record #$id already exists")
