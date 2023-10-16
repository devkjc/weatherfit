package com.toy.weatherfit.batch.service

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class BatchParamService(
    private val jdbcTemplate: JdbcTemplate
) {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun getLastDateToYesterday(): List<String> {
        val lastDate = LocalDate.parse(getLastDate() ?: "20231001", formatter).plusDays(1)
        val yesterday = LocalDate.now().minusDays(1)
        return getDatesBetween(lastDate, yesterday)
    }

    fun getLastDate(): String? {
        val query = "SELECT MAX(PARAMETER_VALUE)\n" +
                "FROM BATCH_JOB_EXECUTION_PARAMS join\n" +
                "     batch_job_execution bje on bje.JOB_EXECUTION_ID = BATCH_JOB_EXECUTION_PARAMS.JOB_EXECUTION_ID\n" +
                "WHERE PARAMETER_TYPE = 'java.lang.String'\n" +
                "  AND PARAMETER_NAME = 'date'\n" +
                "  AND bje.EXIT_CODE = 'COMPLETED'"
        return jdbcTemplate.queryForObject(query, String::class.java)
    }

    fun getDatesBetween(start: LocalDate, end: LocalDate): List<String> {
        val datesBetween = mutableListOf<String>()
        var currentDate = start
        while (!currentDate.isAfter(end)) {
            datesBetween.add(currentDate.format(formatter))
            currentDate = currentDate.plusDays(1)
        }
        return datesBetween
    }
}