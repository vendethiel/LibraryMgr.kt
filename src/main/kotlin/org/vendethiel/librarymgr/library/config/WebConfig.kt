package org.vendethiel.librarymgr.library.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@EnableWebMvc
@Configuration
class WebConfig : WebMvcConfigurer {
    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer
            .favorParameter(true)
            .parameterName("mediaType")
            // Ignore accept header so that we can receive JSON by default in the browser
            //  (without needing ?mediaType=json)
            .ignoreAcceptHeader(true)
            .defaultContentType(MediaType.APPLICATION_JSON)
//            .mediaType("html", MediaType.TEXT_HTML)
//            .mediaType("xml", MediaType.APPLICATION_XML)
            .mediaType("json", MediaType.APPLICATION_JSON)
    }
}