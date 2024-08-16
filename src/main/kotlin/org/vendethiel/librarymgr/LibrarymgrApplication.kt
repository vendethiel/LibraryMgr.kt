package org.vendethiel.librarymgr

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
//@EnableAsync
@EnableScheduling
class LibrarymgrApplication

fun main(args: Array<String>) {
    runApplication<LibrarymgrApplication>(*args)
}
