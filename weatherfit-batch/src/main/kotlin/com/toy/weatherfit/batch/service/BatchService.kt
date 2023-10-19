package com.toy.weatherfit.batch.service

import org.springframework.batch.core.*
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Service
class BatchService(
    private val jobLauncher: JobLauncher,
    private val weatherJob: Job,
    private val weatherListJob: Job,
    private val batchParamService: BatchParamService
) {

    fun runWeatherJob(date: String) {

        require(isValidDate(date)) { "오늘 이전의 날짜만 사용할 수 있습니다." }

        try {
            val result = jobLauncher.run(weatherJob, buildJobParameters(date))
            handleJobResult(result, date)
        } catch (exception: JobExecutionException) {
            handleJobExecutionException(exception)
        }
    }

    fun runWeatherListJob(startDate: String, endDate: String) {

        require(isValidDate(startDate) && isValidDate(endDate)) { "오늘 이전의 날짜만 사용할 수 있습니다." }

        try {
            val jobParameters = JobParametersBuilder()
                .addString("startDate", startDate)
                .addString("endDate", endDate)
                .toJobParameters()

            val result = jobLauncher.run(weatherListJob, jobParameters)
            if (result.exitStatus.equals(ExitStatus.COMPLETED)) {
                println(" Job Success == $startDate ~ $endDate == ${result.endTime} ")
            } else {
                throw result.allFailureExceptions.first()
            }
        } catch (exception: JobExecutionException) {
            handleJobExecutionException(exception)
        }
    }

    fun isValidDate(date: String): Boolean {
        return try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE).isBefore(LocalDate.now())
        } catch (e: DateTimeParseException) {
            throw IllegalArgumentException("올바른 날짜 형식이 아닙니다.")
        }
    }

    fun blankRunWeatherJob() =
        batchParamService.getLastDateToYesterday().forEach { runWeatherJob(it) }

    private fun buildJobParameters(date: String) =
        JobParametersBuilder().addString("date", date).toJobParameters()

    private fun handleJobResult(result: JobExecution, date: String) {
        if (result.exitStatus.equals(ExitStatus.COMPLETED)) {
            println(" Job Success == $date == ${result.endTime} ")
        } else {
            throw result.allFailureExceptions.first()
        }
    }

    private fun handleJobExecutionException(exception: JobExecutionException) {
        when (exception) {
            is JobExecutionAlreadyRunningException, is JobInstanceAlreadyCompleteException -> println("=SKIP=")
            else -> exception.printStackTrace()
        }
    }
}
