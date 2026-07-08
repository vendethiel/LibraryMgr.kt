package org.vendethiel.librarymgr.library.service

import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.util.concurrent.TimeUnit
import org.vendethiel.librarymgr.library.model.apiresponse.Book as APIBook

@Service
class BookRefresherService(private val service: BookService, private val bookRefresherWebClient: WebClient) {
    companion object {
        val logger: Logger = LoggerFactory.getLogger(BookRefresherService::class.java)
    }

    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    suspend fun refreshBooks() {
        logger.info("Refreshing books...")
        val apiBooks = bookRefresherWebClient
            .get()
            .uri("https://rawgit.com/vendethiel/3e9e79cda2e76b27e56bdb2abeba09a6/raw/books.json")
            .retrieve()
            .awaitBody<List<APIBook>>()
        logger.info("...${apiBooks.size} Books to process!")
        runBlocking {
            val unknown = apiBooks.filter { service.getByISBN(it.isbn) == null }
            logger.info("...${unknown.size} Books to create")
            unknown.forEach {
                service.create(it.toModel())
            }
        }
    }
}