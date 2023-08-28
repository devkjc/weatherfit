package com.toy.style

import com.toy.style.dto.UserStyleRequest
import com.toy.style.repository.StyleRepository
import com.toy.style.repository.UserStyleRepository
import com.toy.style.service.StyleService
import com.toy.test.DataServiceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

@DataServiceTest
class StyleServiceTest(
    private val styleService: StyleService,
) {

    @Test
    @DisplayName("스타일 조회")
    fun getStyles() {
        val styles = styleService.getStyles()
        assertThat(styles.size).isGreaterThan(0)
    }

    @Test
    @DisplayName("스타일 추가 성공")
    fun userStyleCreateSuccess() {
        val userStyleRequest = UserStyleRequest(1L, listOf(1L, 2L, 3L, 5L))

        val userStyleResponse = styleService.saveUserStyle(userStyleRequest)

        assertThat(userStyleRequest.styleIdList.size).isEqualTo(4)
        assertThat(userStyleResponse.styles.size).isEqualTo(4)
        assertThat(userStyleResponse.userId).isEqualTo(1)
        assertThat(userStyleResponse.styles[0].id).isEqualTo(1)
    }

    @Test
    @DisplayName("스타일 추가 실패")
    fun userStyleCreateFail() {
        val userStyleRequest = UserStyleRequest(1L, listOf(0L))

        assertThrows<IllegalArgumentException> {
            styleService.saveUserStyle(userStyleRequest)
        }
    }
}