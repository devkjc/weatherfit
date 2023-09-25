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
    val values: List<WeatherValue>
) {
    data class WeatherValue(
        val name: String,
        val value: Any,
        val unit: String
    )

    companion object {
        fun of(currentWeatherResponse: CurrentWeatherResponse, mapCode: MapCode): CurrentWeather {
            val item = currentWeatherResponse.response.body.items.item
            val first = item[0]
            val values = item.map { it.toWeatherValue() }.toMutableList()

            val windSpeed = values.firstOrNull { it.name == "풍속" }?.value ?: 0.0

            values.add(WeatherValue("풍속 구간", classifyWindSpeed(anyToDoubleOrZero(windSpeed)), "-"))

            return CurrentWeather(
                first.baseDate,
                first.baseTime,
                first.nx,
                first.ny,
                mapCode.city,
                mapCode.gu,
                mapCode.dong,
                values
            )
        }

        private fun classifyWindSpeed(windSpeed: Double): String {
            return when {
                windSpeed < 4.0 -> "약한 바람"
                windSpeed < 9.0 -> "약간 강한 바람"
                windSpeed < 14.0 -> "강한 바람"
                else -> "매우 강한 바람"
            }
        }

        private fun anyToDoubleOrZero(value: Any): Double {
            return try {
                value.toString().toDouble()
            } catch (e: NumberFormatException) {
                0.0
            }
        }
    }
}

fun Item.toWeatherValue(): CurrentWeather.WeatherValue {
    return if (category == Category.PTY) {
        CurrentWeather.WeatherValue(category.categoryName, Category.PTYCode.getCodeName(obsrValue.toInt()), category.unit)
    } else {
        CurrentWeather.WeatherValue(category.categoryName, obsrValue.toDouble(), category.unit)
    }
}