package com.toy.weatherfit.batch.config

import com.toy.weatherfit.weather.domain.WeatherAsos
import com.toy.weatherfit.weather.dto.WeatherCsvResponse
import com.toy.weatherfit.weather.service.WeatherService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemStreamException
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.UrlResource
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class WeatherBatchConfiguration(
    private val weatherService: WeatherService,
) {

    @Value("\${weather.kma.service-key}")
    private lateinit var serviceKey: String

    @Bean(name = ["weatherJob"])
    fun weatherJob(jobRepository: JobRepository, weatherStep: Step, observatoryStep: Step): Job {
        return JobBuilder("weatherJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(weatherStep)
            .next(observatoryStep)
            .build()
    }

    @Bean(name = ["weatherListJob"])
    fun weatherListJob(jobRepository: JobRepository, weatherListStep: Step, observatoryStep: Step): Job {
        return JobBuilder("weatherListJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(weatherListStep)
            .next(observatoryStep)
            .build()
    }

    @Bean(name = ["weatherStep"])
    @JobScope
    fun weatherStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        @Value("#{jobParameters['date']}") date: String,
    ): Step {
        return StepBuilder("weatherStep", jobRepository)
            .chunk<WeatherCsvResponse, WeatherAsos>(10, transactionManager)
            .reader(weatherItemReader(date))
            .processor { item -> WeatherAsos.of(item) }
            .writer { weatherService.saveWeatherAsosList(it.items) }
            .faultTolerant()
            .retry(ItemStreamException::class.java)
            .retryLimit(3)
            .skip(JobExecutionAlreadyRunningException::class.java)
            .build()
    }

    @Bean(name = ["weatherListStep"])
    @JobScope
    fun weatherListStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
        @Value("#{jobParameters['startDate']}") startDate: String,
        @Value("#{jobParameters['endDate']}") endDate: String,
    ): Step {
        return StepBuilder("weatherStep", jobRepository)
            .chunk<WeatherCsvResponse, WeatherAsos>(50, transactionManager)
            .reader(weatherListItemReader(startDate, endDate))
            .processor { item -> WeatherAsos.of(item) }
            .writer { weatherService.saveWeatherAsosList(it.items) }
            .faultTolerant()
            .retry(ItemStreamException::class.java)
            .retryLimit(3)
            .skip(JobExecutionAlreadyRunningException::class.java)
            .build()
    }

    @Bean(name = ["observatoryStep"])
    @JobScope
    fun observatoryStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
    ): Step {
        return StepBuilder("observatoryStep", jobRepository)
            .tasklet({ _, _ ->
                weatherService.updateStnNm()
                RepeatStatus.FINISHED
            }, transactionManager)
            .build()
    }

    fun weatherItemReader(date: String): ItemReader<WeatherCsvResponse> {
        return FlatFileItemReaderBuilder<WeatherCsvResponse>()
            .name("weatherItemReader")
            .resource(UrlResource("https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd.php?tm=$date&stn=0&help=0&authKey=$serviceKey"))
            .lineTokenizer {
                val tokenizer = DelimitedLineTokenizer(",")
                fieldSet(tokenizer, it, true)
            }
            .fieldSetMapper(fieldMapper())
            .build()
    }

    private fun fieldSet(tokenizer: DelimitedLineTokenizer, it: String?, isLastBlank: Boolean = false): FieldSet {
        val names = mutableListOf(
            "tm", "stn", "wsAvg", "wrDay", "wdMax", "wsMax", "wsMaxTm", "wdIns", "wsIns", "wsInsTm", "taAvg", "taMax", "taMaxTm", "taMin", "taMinTm", "tdAvg", "tsAvg", "tgMin", "hmAvg", "hmMin", "hmMinTm", "pvAvg", "evS",
            "evL", "fgDur", "paAvg", "psAvg", "psMax", "psMaxTm", "psMin", "psMinTm", "caTot", "ssDay", "ssDur", "ssCmb", "siDay", "si60MMax", "si60MMaxTm", "rnDay", "rnD99", "rnDur", "rn60MMax", "rn60MMaxTm", "rn10MMax", "rn10MMaxTm",
            "rnPowMax", "rnPowMaxTm", "sdNew", "sdNewTm", "sdMax", "sdMaxTm", "te05", "te10", "te15", "te30", "te50"
        )
        if (isLastBlank) {
            names.add("blank")
        }
        tokenizer.setNames(*names.toTypedArray())
        return tokenizer.tokenize(it)
    }

    fun weatherListItemReader(startDate: String, endDate: String): ItemReader<WeatherCsvResponse> {
        return FlatFileItemReaderBuilder<WeatherCsvResponse>()
            .name("weatherListItemReader")
            .resource(UrlResource("https://apihub.kma.go.kr/api/typ01/url/kma_sfcdd3.php?tm1=$startDate&tm2=$endDate&stn=0&authKey=$serviceKey"))
            .lineTokenizer {
                val tokenizer = CustomLineTokenizer()
                fieldSet(tokenizer, it)
            }
            .fieldSetMapper(fieldMapper())
            .build()
    }

    private fun fieldMapper(): (FieldSet) -> WeatherCsvResponse = {
        WeatherCsvResponse(
            tm = it.readString("tm"),
            stn = it.readLong("stn"),
            taAvg = it.readDouble("taAvg"),
            taMin = it.readDouble("taMin"),
            taMax = it.readDouble("taMax"),
            hmAvg = it.readDouble("hmAvg"),
            wsMax = it.readDouble("wsMax"),
            wsAvg = it.readDouble("wsAvg"),
            rnDay = it.readDouble("rnDay"),
            rn60MMax = it.readDouble("rn60MMax"),
            sdNew = it.readDouble("sdNew"),
        )
    }
}