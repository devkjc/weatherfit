package com.toy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiApplication

fun main(args: Array<String>) {
//    println(User(1L).id)
    runApplication<ApiApplication>(*args)
}