package com.toy.weatherfit.batch.service

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class BatchService(
    private val jobLauncher: JobLauncher,
    private val weatherJob: Job,
    private val batchParamService: BatchParamService
) {

    fun runWeatherJob(date: String) {
        println("실행 $date")

        if (isTodayAfterEqualDate(date)) {
            throw IllegalArgumentException("오늘 이전의 날짜만 사용할 수 있습니다.")
        }

        val jobParameters = JobParametersBuilder()
            .addString("date", date)
            .toJobParameters()

        val result = jobLauncher.runCatching {
            run(weatherJob, jobParameters)
        }

        result.onSuccess {
            println(" Job Success == $date == ${it.endTime} ")
        }

        result.onFailure { exception ->
            when (exception) {
                JobExecutionAlreadyRunningException::class.java -> println("=SKIP=")
                else -> exception.printStackTrace()
            }
        }

    }

    fun isTodayAfterEqualDate(date: String): Boolean {
        val parseDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE)
        val now = LocalDate.now()

        return parseDate.isAfter(now)
                || parseDate.isEqual(now)
    }

    fun blankRunWeatherJob() {
        batchParamService.getLastDateToYesterday().forEach {
            runWeatherJob(it)
        }
    }

}