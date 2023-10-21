package com.toy.weatherfit.user.service

import com.toy.weatherfit.user.domain.AgeRange
import com.toy.weatherfit.user.domain.Gender
import com.toy.weatherfit.user.domain.UserRepository
import com.toy.weatherfit.user.dto.UserCreateRequest
import com.toy.weatherfit.user.dto.UserResponse
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class UserService(
    private val userRepository: UserRepository
) {

    /**
     * 닉네임 중복 검사
     * 가입 가능 시 true 중복 되어 불가 시 false
     * @param nickName String
     * @return Boolean
     */
    fun nickNameDuplicateCheck(nickName: String): Boolean {
        return userRepository.countByNickName(nickName) == 0L
    }


    /**
     * 회원 가입
     * @param userCreateRequest UserCreateRequest
     * @return UserResponse
     */
    fun createUser(userCreateRequest: UserCreateRequest): UserResponse {
        if (!nickNameDuplicateCheck(userCreateRequest.nickName)) {
            throw IllegalArgumentException("닉네임이 중복 되었습니다.")
        }
        return UserResponse.of(userRepository.save(userCreateRequest.toEntity()))
    }

    /**
     * 유저 조회
     * @param userId Long
     * @return UserResponse
     */
    fun getUser(userId: Long): UserResponse {
        return userRepository.findById(userId).map { UserResponse.of(it) }.orElseGet { UserResponse.invalidUser(userId) }
    }

}