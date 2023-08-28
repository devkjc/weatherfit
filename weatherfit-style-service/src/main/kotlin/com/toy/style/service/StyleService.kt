package com.toy.style.service

import com.toy.style.domain.Style
import com.toy.style.domain.UserStyle
import com.toy.style.dto.UserStyleRequest
import com.toy.style.dto.UserStyleResponse
import com.toy.style.repository.StyleRepository
import com.toy.style.repository.UserStyleRepository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class StyleService(
    private val userStyleRepository: UserStyleRepository,
    private val styleRepository: StyleRepository
) {

    /**
     * 스타일 조회
     * @return List<Style>
     */
    fun getStyles(): List<Style> {
        return styleRepository.findAll()
    }

    /**
     * 유저 스타일 저장
     * @param userStyleRequest UserStyleRequest
     * @return UserStyleResponse
     */
    fun saveUserStyle(userStyleRequest: UserStyleRequest): UserStyleResponse {
        val styleList = styleRepository.findAllById(userStyleRequest.styleIdList)

        require(styleList.size == userStyleRequest.styleIdList.size) {
            "존재하지 않는 스타일을 선택했습니다."
        }

        val userStyles = styleList.map { style ->
            UserStyle(userStyleRequest.userId, style)
        }

        val savedUserStyles = userStyleRepository.saveAll(userStyles)
        return UserStyleResponse.of(savedUserStyles)
    }
}