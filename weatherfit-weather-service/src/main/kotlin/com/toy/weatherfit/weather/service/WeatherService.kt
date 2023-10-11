package com.toy.weatherfit.weather.service

import com.toy.weatherfit.weather.domain.WeatherAsos
import com.toy.weatherfit.weather.domain.WeatherAsosRepository
import org.springframework.cache.interceptor.SimpleKeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import javax.crypto.KeyGenerator

@Service
class WeatherService(
    private val weatherAsosRepository: WeatherAsosRepository
) {

    fun saveWeatherAsosList(items: List<WeatherAsos>) {
        weatherAsosRepository.saveAll(items)
    }

    fun updateStnNm() {
        weatherAsosRepository.updateStnNm()
    }

    fun getWeatherByStnNoAndDate(stnNo: Long, date: String): WeatherAsos? {
        return weatherAsosRepository.findByStnNoAndDate(stnNo, date)
    }

}