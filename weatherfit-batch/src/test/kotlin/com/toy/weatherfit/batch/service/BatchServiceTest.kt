package com.toy.weatherfit.batch.service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import kotlin.test.Ignore

@Ignore
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BatchServiceTest(
    private val batchService: BatchService
){

    @Test
    fun blankRunWeatherJobTest() {
        batchService.blankRunWeatherJob()
    }
}
