package com.toy.user.service

import com.toy.user.domain.UserRepository
import com.toy.user.dto.UserCreateRequest
import com.toy.user.dto.UserResponse
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

}