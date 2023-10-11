package com.toy.weatherfit.weather

import com.toy.weatherfit.test.DataServiceTest
import com.toy.weatherfit.weather.service.WeatherCall
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@DataServiceTest
class WeatherCallTest(
    private val weatherCall: WeatherCall
){

    @Test
    fun getCurrentTimeTest() {

        val time0540 = LocalTime.of(5,40)
        val time0541 = LocalTime.of(5,41)

        assertThat(getTimeFormat(time0540)).isEqualTo("0400")
        assertThat(getTimeFormat(time0541)).isEqualTo("0500")

    }

    @Test
    fun callCurrentWeather() {
        val callCurrentWeather = weatherCall.callCurrentWeather(37.5889503, 127.0644882)
        println(callCurrentWeather)
    }

    @Test
    fun callDailyWeather() {
        weatherCall.callDailyWeather("20231004", "20231004", 108, true)
    }

    private fun getTimeFormat(now: LocalTime): String? = when {
        now.minute > 40 -> now.format(DateTimeFormatter.ofPattern("HH00"))
        else -> now.minusHours(1).format(DateTimeFormatter.ofPattern("HH00"))
    }
}

