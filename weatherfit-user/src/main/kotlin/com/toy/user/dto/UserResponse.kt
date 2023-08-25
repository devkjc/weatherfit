package com.toy.user.dto

import com.toy.user.domain.AgeRange
import com.toy.user.domain.Gender
import com.toy.user.domain.User
import jakarta.validation.constraints.*

data class UserResponse(
    val id: Long,
    val nickName: String,
    val ageRange: AgeRange,
    val gender: Gender,
    val height: Int
){

    companion object{
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.id!!,
                ageRange = user.ageRange,
                nickName = user.nickName,
                gender = user.gender,
                height = user.height
            )
        }
    }



}
