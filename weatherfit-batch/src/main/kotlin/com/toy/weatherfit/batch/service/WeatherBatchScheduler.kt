package com.toy.weatherfit.batch.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class WeatherBatchScheduler(
    private val batchService: BatchService
){

    @Scheduled(cron = "0 10 0 * * ?", zone = "Asia/Seoul")
    fun weatherScheduler() {
        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE)
        batchService.runWeatherJob(yesterday)
    }
}