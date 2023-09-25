package com.toy.weatherfit.user.service

import com.toy.weatherfit.test.DataServiceTest
import com.toy.weatherfit.user.domain.AgeRange
import com.toy.weatherfit.user.domain.Gender
import com.toy.weatherfit.user.domain.UserRepository
import com.toy.weatherfit.user.dto.UserCreateRequest
import com.toy.weatherfit.user.service.UserService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@DataServiceTest
class UserServiceTest(
    private val userService: UserService,
    private val userRepository: UserRepository
) {

    /**
     * 중복 검사 테스트
     */
    @Test
    fun duplicateUserTest() {
        val nickName = "닉네임"
        val userRequest = UserCreateRequest(nickName, AgeRange.AGE0_9, Gender.MALE, 154)
        userService.createUser(userRequest)

        val duplicateCheckFalse = userService.nickNameDuplicateCheck(nickName)
        val duplicateCheckTrue = userService.nickNameDuplicateCheck(nickName + "2")

        assertThat(duplicateCheckFalse).isFalse()
        assertThat(duplicateCheckTrue).isTrue()
    }

    /**
     * 회원 가입 테스트
     */
    @Test
    fun createUserTest() {
        val userRequest = UserCreateRequest("닉네임", AgeRange.AGE0_9, Gender.MALE, 154)

        val createUser = userService.createUser(userRequest)

        println(createUser.toString())

        assertThat(createUser.id).isNotNull()
        assertThat(createUser.nickName).isEqualTo("닉네임")
        assertThat(createUser.ageRange).isEqualTo(AgeRange.AGE0_9)
        assertThat(createUser.height).isEqualTo(154)
        assertThat(createUser.gender).isEqualTo(Gender.MALE)
    }
}