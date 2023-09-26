package com.toy.weatherfit.api

import com.toy.weatherfit.weather.service.WeatherCall
import com.toy.weatherfit.weather.dto.CurrentWeather
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weather")
class WeatherCallController(
    private val weatherCall: WeatherCall
) {

    @GetMapping
    fun test(
        @RequestParam lat: Double,
        @RequestParam lon: Double
    ): CurrentWeather? {
        return weatherCall.callCurrentWeather(lat, lon)
    }
}