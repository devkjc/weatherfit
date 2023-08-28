package com.toy.user.dto

import com.toy.user.domain.AgeRange
import com.toy.user.domain.Gender
import com.toy.user.domain.User
import jakarta.validation.constraints.*

data class UserCreateRequest(

    @field:NotBlank
    val nickName: String,

    val ageRange: AgeRange,

    val gender: Gender,

    @field:Positive
    val height: Int
){

    fun toEntity() : User {
        return User.fixture(nickName, ageRange, gender, height)
    }

}
