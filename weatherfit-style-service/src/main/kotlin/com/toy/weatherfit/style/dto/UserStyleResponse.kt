package com.toy.weatherfit.style.dto

import com.toy.weatherfit.style.domain.Style
import com.toy.weatherfit.style.domain.UserStyle

data class UserStyleResponse(
    val userId: Long,
    val styles: List<Style>
) {
    companion object {
        fun of(userStyleResponse: List<UserStyle>): UserStyleResponse {
            return UserStyleResponse(
                userId = userStyleResponse[0].userId,
                styles = userStyleResponse.map { userStyle -> userStyle.style }
            )
        }
    }
}
