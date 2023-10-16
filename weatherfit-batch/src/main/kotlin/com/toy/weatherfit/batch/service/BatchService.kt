package com.toy.weatherfit.batch.service

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.stereotype.Service

@Service
class BatchService(
    private val jobLauncher: JobLauncher,
    private val weatherJob: Job,
    private val batchParamService: BatchParamService
)  {

    fun runWeatherJob(date: String) {
        println("실행 $date")

        val jobParameters = JobParametersBuilder()
            .addString("date", date)
            .toJobParameters()

        val jobExecution = jobLauncher.run(weatherJob, jobParameters)
        println("Weather Batch Job 실행: ${jobExecution.status}")
    }

    fun blankRunWeatherJob() {
        batchParamService.getLastDateToYesterday().forEach{
            runWeatherJob(it)
        }
    }

}