package com.photo.server.starsnap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StarsnapApplication

fun main(args: Array<String>) {
    runApplication<StarsnapApplication>(*args)
}
