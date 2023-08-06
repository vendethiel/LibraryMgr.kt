package org.vendethiel.librarymgr.library.exception

abstract class RecordNotFoundException(record: String, id: Long) : RuntimeException("Could not find $record #$id")
