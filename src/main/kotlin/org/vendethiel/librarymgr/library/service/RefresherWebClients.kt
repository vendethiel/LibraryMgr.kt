package org.vendethiel.librarymgr.library.service

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class RefresherWebClients(private val webClientFactory: WebClientFactory) {
    @Bean
    fun bookRefresherWebClient(): WebClient =
        webClientFactory.builder().baseUrl("").build()
}