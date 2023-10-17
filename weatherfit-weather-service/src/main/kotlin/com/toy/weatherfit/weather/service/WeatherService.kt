package com.toy.weatherfit.weather.service

import com.toy.weatherfit.weather.domain.WeatherAsos
import com.toy.weatherfit.weather.domain.WeatherAsosRepository
import org.springframework.stereotype.Service

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
        return weatherAsosRepository.findById(WeatherAsos.WeatherAsosId(stnNo, date))
    }

}