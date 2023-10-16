package com.toy.weatherfit.batch.config

import com.toy.weatherfit.weather.domain.WeatherAsos
import com.toy.weatherfit.weather.dto.WeatherCsvResponse
import com.toy.weatherfit.weather.service.WeatherService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
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

    @Value("\${weather.kma.service-key}") // YAML 파일의 키 이름을 지정합니다.
    private lateinit var serviceKey: String

    @Bean(name = ["weatherJob"])
    fun weatherJob(jobRepository: JobRepository, weatherStep: Step, observatoryStep: Step): Job {
        return JobBuilder("weatherJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(weatherStep)
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
//            .resource(UrlResource("https://apihub.kma.go.kr/api/typ012/url/kma_sfcdd.php?tm=$date&stn=0&help=0&authKey=$serviceKey"))
            .lineTokenizer {
                // DelimitedLineTokenizer 구성
                val tokenizer = DelimitedLineTokenizer()
                tokenizer.setDelimiter(",") // CSV 파일에서 필드를 쉼표로 구분
                tokenizer.setNames("tm", "stn", "wsAvg", "wrDay", "wdMax", "wsMax", "wsMaxTm", "wdIns", "wsIns", "wsInsTm", "taAvg", "taMax", "taMaxTm", "taMin", "taMinTm", "tdAvg", "tsAvg", "tgMin", "hmAvg", "hmMin", "hmMinTm", "pvAvg", "evS",
                    "evL", "fgDur", "paAvg", "psAvg", "psMax", "psMaxTm", "psMin", "psMinTm", "caTot", "ssDay", "ssDur", "ssCmb", "siDay", "si60MMax", "si60MMaxTm", "rnDay", "rnD99", "rnDur", "rn60MMax", "rn60MMaxTm", "rn10MMax", "rn10MMaxTm",
                    "rnPowMax", "rnPowMaxTm", "sdNew", "sdNewTm", "sdMax", "sdMaxTm", "te05", "te10", "te15", "te30", "te50","blank") // 각 필드의 이름 설정
                tokenizer.tokenize(it)
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
            hmAvg =  it.readDouble("hmAvg"),
            wsMax = it.readDouble("wsMax"),
            wsAvg = it.readDouble("wsAvg"),
            rnDay = it.readDouble("rnDay"),
            rn60MMax = it.readDouble("rn60MMax"),
            sdNew = it.readDouble("sdNew"),
        )
    }
}