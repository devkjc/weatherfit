package com.toy.api

import com.toy.user.dto.UserCreateRequest
import com.toy.user.dto.UserResponse
import com.toy.user.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/user")
@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun saveUser(@Valid @RequestBody userCreateRequest: UserCreateRequest): UserResponse {
        return userService.createUser(userCreateRequest)
    }

}