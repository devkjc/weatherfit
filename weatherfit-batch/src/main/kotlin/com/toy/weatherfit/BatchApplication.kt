package com.toy.weatherfit

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableBatchProcessing
@SpringBootApplication
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}