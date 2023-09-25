package com.toy.weatherfit.user.dto

import com.toy.weatherfit.user.domain.AgeRange
import com.toy.weatherfit.user.domain.Gender
import com.toy.weatherfit.user.domain.User
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
