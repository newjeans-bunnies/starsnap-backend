package com.photo.server.starsnap.adapter_lambda_s3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class StarsnapLambdaApplication
fun main(args: Array<String>) {
    runApplication<StarsnapLambdaApplication>(*args)
}