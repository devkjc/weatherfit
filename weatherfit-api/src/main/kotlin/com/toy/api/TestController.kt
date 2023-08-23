package com.toy.api

import com.toy.user.domain.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun testUser(): User {
        return User(1L)
    }
}

