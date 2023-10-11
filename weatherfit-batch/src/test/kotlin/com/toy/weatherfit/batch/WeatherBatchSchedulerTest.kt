package com.toy.weatherfit.batch

import com.toy.weatherfit.BatchApplication
import com.toy.weatherfit.test.DataServiceTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestConstructor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.test.Ignore

@Ignore
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@ContextConfiguration(classes = [WeatherBatchConfiguration::class, BatchApplication::class])
class WeatherBatchSchedulerTest {

    @Autowired
    private lateinit var weatherJob: Job

    @Autowired
    private lateinit var jobLauncher: JobLauncher

    @Test
    fun runWeatherJob() {

        val startDate = LocalDate.of(2019,1,1)
        val endDate = LocalDate.of(2021,8,30)

        val daysBetween = ChronoUnit.DAYS.between(startDate, endDate)
        val dateList = (0..daysBetween).map { startDate.plusDays(it) }

        dateList.forEach {
            val jobParameters = JobParametersBuilder()
                .addString("date", it.format(DateTimeFormatter.BASIC_ISO_DATE)) // 예시로 날짜 파라미터 추가 (원하는 날짜로 설정)
                .toJobParameters()

            val launchJob = jobLauncher.run(weatherJob, jobParameters)
            assertEquals(ExitStatus.COMPLETED, launchJob.getExitStatus());
        }
    }
}