package com.toy.weatherfit.weather.service

import org.springframework.stereotype.Service

/*
    TODO
        1. Kafka에 매일 00시에 전 날 날짜 데이터를 저장한다.
        2. 해당 지점, 날짜데이터를 Kafka에서 조회해 데이터를 가져온다.
 */
@Service
class WeatherService(
    private val weatherCall: WeatherCall
) {

    fun savePastWeather(startDate: String, endDate: String, stdId: Long) {
        val dailyWeather = weatherCall.callDailyWeather(startDate, endDate, stdId)

    }

}