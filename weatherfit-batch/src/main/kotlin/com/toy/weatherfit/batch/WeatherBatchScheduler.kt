package com.toy.weatherfit.batch

import com.toy.weatherfit.weather.service.ObservatoryService
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class WeatherBatchScheduler(
    private val jobLauncher: JobLauncher,
    private val weatherJob: Job
) {

    @Scheduled(cron = "0 10 0 * * ?")
    fun runWeatherJob() {

        val yesterday = LocalDate.now().minusDays(1).format(DateTimeFormatter.BASIC_ISO_DATE)

        println("실행 $yesterday")
        val jobParameters = JobParametersBuilder()
            .addString("date", yesterday) // 예시로 날짜 파라미터 추가 (원하는 날짜로 설정)
            .toJobParameters()

        val jobExecution = jobLauncher.run(weatherJob, jobParameters)
        println("Weather Batch Job 실행: ${jobExecution.status}")
    }
}