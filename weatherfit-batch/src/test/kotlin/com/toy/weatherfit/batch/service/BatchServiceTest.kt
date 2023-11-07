package com.toy.weatherfit.batch.service

import com.toy.weatherfit.weather.service.WeatherService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.Ignore

//@Ignore
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BatchServiceTest(
    private val batchService: BatchService,
    private val batchParamService: BatchParamService,
    private val weatherService: WeatherService
){

    @Test
    fun runWeatherJobTest() {
        batchService.runWeatherJob("20231001")
    }

    @Test
    fun runMissingDatesWeatherJobTest() {
        weatherService.getMissingDates().forEach {
            batchService.runWeatherJob(it)
        }
    }

    @Test
    fun runWeatherListJobTest() {
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val startDate = LocalDate.of(2023, 11, 1).format(formatter)
        val endDate = LocalDate.of(2023,11,6).format(formatter)
        batchService.runWeatherListJob(startDate, endDate)
    }

    @Test
    fun blankRunWeatherJobTest() {
        batchService.blankRunWeatherJob()
    }

    @Test
    fun isValidDate() {
        assertThat(batchService.isValidDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE))).isFalse()
        assertThat(batchService.isValidDate("30000101")).isFalse()
        assertThat(batchService.isValidDate("20231015")).isTrue()
    }
}
