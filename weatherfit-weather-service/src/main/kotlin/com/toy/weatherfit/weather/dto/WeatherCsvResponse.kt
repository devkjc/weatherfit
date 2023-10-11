package com.toy.weatherfit.weather.dto

data class WeatherCsvResponse(
    val stn: Long,             // 국내 지점번호
    val tm: String,            // 관측일 (KST)

    val taAvg: Double,         // 일 평균기온 (C)
    val taMin: Double,         // 최저기온 (C)
    val taMax: Double,         // 최고기온 (C)

    val hmAvg: Double,         // 일 평균 상대습도 (%)

    val wsMax: Double,         // 최대풍속 (m/s)
    val wsAvg: Double,         // 일 평균 풍속 (m/s)

    val rnDay: Double,         // 일 강수량 (mm)
    val rn60MMax: Double,      // 1시간 최다강수량 (mm)
    val sdNew: Double,         // 최심 신적설 (cm)
)