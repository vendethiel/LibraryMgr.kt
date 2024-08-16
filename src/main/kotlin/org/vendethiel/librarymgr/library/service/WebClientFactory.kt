package org.vendethiel.librarymgr.library.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class WebClientFactory {
    fun build() = builder().build()

    fun builder() = WebClient.builder()
}