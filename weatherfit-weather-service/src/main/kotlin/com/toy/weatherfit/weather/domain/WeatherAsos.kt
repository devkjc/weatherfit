package com.toy.weatherfit.weather.domain

import com.toy.weatherfit.weather.dto.WeatherCsvResponse
import com.toy.weatherfit.weather.dto.WeatherResponse
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.io.Serializable

@Entity
class WeatherAsos (
    // 지점 번호
    val stnNo: Long,
    // 지점 이름
    val stnNm: String,
    // 날짜
    val date: String,
    // 평균 기온
    val avgTa: Double,
    // 최저 기온
    val minTa: Double,
    // 최고 기온
    val maxTa: Double,
    // 평균 상대 습도
    val avgRhm: Double,
    // 최대 풍속
    val maxWs: Double,
    // 평균 풍속
    val avgWs: Double,
    // 일 강수량
    val rnDay: Double,
    // 1시간 최다 강수량
    val hr1MaxRn: Double,
    // 하루 강설량
    val sdNew: Double,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) : Serializable {

    companion object {
        fun of(data: WeatherCsvResponse): WeatherAsos {
            return WeatherAsos(
                stnNo = data.stn,
                stnNm = "data.stnNm",
                date = data.tm,
                avgTa = data.taAvg,
                minTa = data.taMin,
                maxTa = data.taMax,
                avgRhm = data.hmAvg,
                maxWs = data.wsMax,
                avgWs = data.wsAvg,
                rnDay = data.rnDay,
                hr1MaxRn = data.rn60MMax,
                sdNew = data.sdNew,
            )
        }
    }

}