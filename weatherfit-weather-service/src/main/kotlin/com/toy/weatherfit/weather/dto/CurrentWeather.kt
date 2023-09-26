package com.toy.weatherfit.weather.dto

import com.toy.weatherfit.weather.domain.MapCode

data class CurrentWeather(
    val baseDate: String,
    val baseTime: String,
    val nx: Int,
    val ny: Int,
    val city: String,
    val gu: String,
    val dong: String,
    val temperature: Double,          // 기온
    val humidity: Double,             // 평균 상대 습도
    val rainType: String,             // 강수형태
    val rainAmount: Double,           // 강수량
    val windSpeed: Double,            // 풍속
    val windType: String              // 풍속
) {

    companion object {
        fun of(currentWeatherResponse: CurrentWeatherResponse, mapCode: MapCode): CurrentWeather {
            val firstItem = currentWeatherResponse.response.body.items.item[0]

            return CurrentWeather(
                baseDate = firstItem.baseDate,
                baseTime = firstItem.baseTime,
                nx = firstItem.nx,
                ny = firstItem.ny,
                city = mapCode.city,
                gu = mapCode.gu,
                dong = mapCode.dong,
                temperature = getValue(currentWeatherResponse, Category.T1H),
                humidity = getValue(currentWeatherResponse, Category.REH),
                rainType = Category.PTYCode.getCodeName(getValue(currentWeatherResponse, Category.PTY).toInt()),
                rainAmount = getValue(currentWeatherResponse, Category.RN1),
                windSpeed = getValue(currentWeatherResponse, Category.WSD),
                windType = classifyWindSpeed(getValue(currentWeatherResponse, Category.WSD))
            )
        }

        private fun getValue(currentWeatherResponse: CurrentWeatherResponse, category: Category): Double {
            return currentWeatherResponse.response.body.items.item
                .firstOrNull { it.category == category }
                ?.obsrValue?.toDoubleOrNull() ?: 0.0
        }

        private fun classifyWindSpeed(windSpeed: Double): String {
            return when {
                windSpeed < 4.0 -> "약한 바람"
                windSpeed < 9.0 -> "약간 강한 바람"
                windSpeed < 14.0 -> "강한 바람"
                else -> "매우 강한 바람"
            }
        }
    }
}
