package com.toy.weatherfit.api

import com.toy.weatherfit.user.dto.UserCreateRequest
import com.toy.weatherfit.user.dto.UserResponse
import com.toy.weatherfit.user.service.UserService
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

    /**
     * 유저 회원가입
     * @param userCreateRequest UserCreateRequest
     * @return UserResponse
     */
    //TODO 추후 SNS 로그인을 도입 해 정보를 따로 받아오게만들기.
    @PostMapping
    fun saveUser(@Valid @RequestBody userCreateRequest: UserCreateRequest): UserResponse {
        return userService.createUser(userCreateRequest)
    }

}