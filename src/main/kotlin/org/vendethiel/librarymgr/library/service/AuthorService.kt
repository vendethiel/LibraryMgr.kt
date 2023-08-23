package org.vendethiel.librarymgr.library.service

import org.springframework.stereotype.Service
import org.vendethiel.librarymgr.library.exception.AuthorNotFoundException
import org.vendethiel.librarymgr.library.model.Author
import org.vendethiel.librarymgr.library.repository.AuthorRepository
import java.util.*

@Service
class AuthorService(private val repository: AuthorRepository) {
    fun find(id: Long): Author =
        unwrap404(id, repository.findByIdWithBooks(id))

    fun list(): Iterable<Author> =
        repository.findAllWithBooks()

    private fun unwrap404(id: Long, author: Optional<Author>): Author =
        author.orElseThrow { AuthorNotFoundException(id) }
}