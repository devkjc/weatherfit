package com.toy.weatherfit.batch.service

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
    private val batchParamService: BatchParamService
){

    @Test
    fun runWeatherJobTest() {
        batchService.runWeatherJob("20231001")
    }

    @Test
    fun runWeatherJobsTest() {
        batchParamService.getDatesBetween(
            LocalDate.of(2013,10,27), LocalDate.of(2023,10,15)).forEach {
                batchService.runWeatherJob(it)
        }
    }

    @Test
    fun runWeatherListJobTest() {
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val startDate = LocalDate.of(2023, 10, 24).format(formatter)
        val endDate = LocalDate.of(2023,10,26).format(formatter)
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
