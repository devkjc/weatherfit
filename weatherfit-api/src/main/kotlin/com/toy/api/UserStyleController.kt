package com.toy.api

import com.toy.style.domain.Style
import com.toy.style.dto.UserStyleRequest
import com.toy.style.dto.UserStyleResponse
import com.toy.style.service.StyleService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/style")
class UserStyleController(
    private val styleService: StyleService,
) {


    //TODO exception 처리 및 response 값 정리 필요
    /**
     * 유저 스타일 저장
     * @param userStyleRequest UserStyleRequest
     * @return UserStyleResponse
     */
    @PostMapping
    fun saveUserStyle(@Valid @RequestBody userStyleRequest: UserStyleRequest): UserStyleResponse {
        return styleService.saveUserStyle(userStyleRequest)
    }

}