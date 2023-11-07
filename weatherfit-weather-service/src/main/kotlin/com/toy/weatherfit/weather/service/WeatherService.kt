package com.toy.weatherfit.weather.service

import com.toy.weatherfit.weather.domain.WeatherAsos
import com.toy.weatherfit.weather.domain.WeatherAsosRepository
import org.springframework.stereotype.Service

@Service
class WeatherService(
    private val weatherAsosRepository: WeatherAsosRepository,
    private val observatoryService: ObservatoryService
) {

    fun saveWeatherAsosList(items: List<WeatherAsos>) {
        weatherAsosRepository.saveAll(items)
    }

    fun updateStnNm() {
        weatherAsosRepository.updateStnNm()
    }

    fun getMissingDates() : List<String> {
        return weatherAsosRepository.getMissingDates()
    }

    fun getWeatherByLatAndLonAndDate(lat: Double, lon: Double, date: String): WeatherAsos? {
        return getWeatherByStnNoAndDate(observatoryService.getObservatory(lat, lon).stdNo, date)
    }

    fun getWeatherByStnNoAndDate(stnNo: Long, date: String): WeatherAsos? {
        return weatherAsosRepository.findById(WeatherAsos.WeatherAsosId(stnNo, date))
    }

}