package com.toy.weatherfit.weather.service

import com.toy.weatherfit.test.DataServiceTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.EnableCaching

@DataServiceTest
@EnableCaching // 캐시 사용을 활성화
class WeatherAsosCacheTest {

    @Autowired
    private lateinit var weatherService: WeatherService

    @Test
    fun cacheTest() {
        weatherService.getWeatherByStnNoAndDate(108, "20231005")
        weatherService.getWeatherByStnNoAndDate(108, "20231005")
        weatherService.getWeatherByStnNoAndDate(108, "20231006")
        weatherService.getWeatherByStnNoAndDate(108, "20231007")
    }
}