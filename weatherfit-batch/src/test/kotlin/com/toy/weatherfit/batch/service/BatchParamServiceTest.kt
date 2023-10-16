package com.toy.weatherfit.batch.service

import com.toy.weatherfit.test.DataServiceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

@DataServiceTest
class BatchParamServiceTest(
    private val batchParamService: BatchParamService
){

    @Test
    fun getLastDateTest() {
        val lastDate = batchParamService.getLastDate()
        println(lastDate)
        assertThat(lastDate).isNotNull()
    }

    @Test
    fun getDatesBetweenTest() {
        val datesBetween = batchParamService.getDatesBetween(LocalDate.of(2023,10,1), LocalDate.of(2023,10,3))
        println(datesBetween)
        assertThat(datesBetween.size).isEqualTo(3)
        assertThat(datesBetween).containsExactlyInAnyOrder("20231001","20231002","20231003")
    }

}




