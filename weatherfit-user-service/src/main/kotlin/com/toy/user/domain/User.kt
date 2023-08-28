package com.toy.user.domain

import jakarta.persistence.*
import java.lang.IllegalArgumentException

@Entity
class User(

    var nickName: String,

    @Enumerated(EnumType.STRING)
    var ageRange: AgeRange,

    @Enumerated(EnumType.STRING)
    val gender: Gender,

    val height: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    init {
        if (nickName.isBlank()) {
            throw IllegalArgumentException("닉네임은 필수 값 입니다.")
        }
        if (height < 0) {
            throw IllegalArgumentException("키는 0보다 커야합니다.")
        }
    }

    companion object {
        fun fixture(
            nickname: String,
            ageRange: AgeRange = AgeRange.AGE20_29,
            gender: Gender = Gender.FEMALE,
            height: Int = 160
        ): User{
            return User(
                nickname, ageRange, gender, height
            )
        }
    }
}