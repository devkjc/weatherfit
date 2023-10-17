package com.toy.weatherfit.weather.service

import com.toy.weatherfit.weather.domain.ObservatoryRepository
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.test.Test

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class WeatherServiceTest(
    private val weatherService: WeatherService,
    private val observatoryRepository: ObservatoryRepository,
) {

    @Test
    fun observatoryFindAll() {
        val findAll = observatoryRepository.findAll()

        assertThat(findAll.filter { it.stdNo == 0L }.size).isEqualTo(0)
    }

    @org.junit.jupiter.api.Test
    fun rangeDate() {
        val startDateStr = "20210110"
        val endDateStr = "20231004"

        // 문자열을 LocalDate로 변환
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val startDate = LocalDate.parse(startDateStr, formatter)
        val endDate = LocalDate.parse(endDateStr, formatter)

        // 두 날짜 사이의 일 수 계산
        val days = ChronoUnit.DAYS.between(startDate, endDate)

        println("두 날짜 사이의 일 수: $days 일")
    }

    @org.junit.jupiter.api.Test
    fun replaceTest() {
        assertThat(("2023-10-04").replace("-", "")).isEqualTo("20231004")
    }

    @Test
    fun updateStdNm() {
        weatherService.updateStnNm()
    }
}