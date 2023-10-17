package com.toy.weatherfit.weather.domain

import com.toy.weatherfit.weather.dto.WeatherCsvResponse
import com.toy.weatherfit.weather.dto.WeatherResponse
import jakarta.persistence.*
import java.io.Serializable

@Entity
class WeatherAsos(
    // 지점 이름
    val stnNm: String,
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
    @EmbeddedId
    var id: WeatherAsosId = WeatherAsosId(),
) : Serializable {

    companion object {
        fun of(data: WeatherCsvResponse): WeatherAsos {

            val weatherAsosId = WeatherAsosId()
            weatherAsosId.stnNo = data.stn
            weatherAsosId.date = data.tm

            return WeatherAsos(
                id = weatherAsosId,
                stnNm = "data.stnNm",
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
    @Embeddable
    class WeatherAsosId : Serializable {
        // 지점 번호
        var stnNo: Long = 0

        // 날짜
        var date: String? = null


        constructor(stnNo: Long, date: String?) {
            this.stnNo = stnNo
            this.date = date
        }

        constructor()
    }
}

