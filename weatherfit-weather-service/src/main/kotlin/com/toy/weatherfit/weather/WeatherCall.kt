package com.toy.weatherfit.weather

import com.toy.weatherfit.weather.domain.MapCodeRepository
import com.toy.weatherfit.weather.dto.CurrentWeather
import com.toy.weatherfit.weather.dto.CurrentWeatherResponse
import com.toy.weatherfit.weather.dto.WeatherResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class WeatherCall(
    private val mapCodeRepository: MapCodeRepository
) {

    @Value("\${weather.service-key}") // YAML 파일의 키 이름을 지정합니다.
    private lateinit var serviceKey: String

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(WeatherCallService::class.java)

    fun callCurrentWeather(lat: Double = 37.6472744, lon: Double = 127.116003): CurrentWeather? {
        val baseDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
        val mapCode = mapCodeRepository.getMapXY(lat, lon)
        val call = apiService.getCurrentWeather(baseDate, getCurrentTime(), mapCode.x.toString(), mapCode.y.toString(), serviceKey)

        val response = call.execute()
        val resultMsgSuccess = response.body()?.response?.header?.resultCode.equals("00")
        return if (response.isSuccessful && response.body() != null && resultMsgSuccess) {
            CurrentWeather.of(response.body()!!, mapCode)
        } else {
            println(response)
            null
        }
    }

    fun callDailyWeather(startDt: String, endDt: String) {
        val call = apiService.getDailyWeather(startDt, endDt, serviceKey)

        val response = call.execute()
        if (response.isSuccessful && response.body() != null && response.body()?.resultCode.equals("00")) {
            println(response)
            println(response.body())
        } else {
            println(response)
        }
    }

    /**
     * 초단기실황 조회 시 40분이 지나야 데이터 조회 가능.
     * 40분이 지나지 않았다면 한시간 전 데이터 조회
     * @return String
     */
    private fun getCurrentTime(): String {
        val now = LocalTime.now()
        return if (now.minute > 40) now.format(DateTimeFormatter.ofPattern("HH00"))
        else now.minusHours(1).format(DateTimeFormatter.ofPattern("HH00"))
    }
}

interface WeatherCallService {
    @GET("/1360000/AsosDalyInfoService/getWthrDataList")
    fun getDailyWeather(
        @Query(value = "startDt", encoded = true) startDt: String,
        @Query(value = "endDt", encoded = true) endDt: String,
        @Query(value = "serviceKey", encoded = true) serviceKey: String,
        @Query(value = "stnIds", encoded = true) stnIds: String = "108",
        @Query(value = "dataType", encoded = true) dataType: String = "JSON",
        @Query(value = "dataCd", encoded = true) dataCd: String = "ASOS",
        @Query(value = "dateCd", encoded = true) dateCd: String = "DAY"
    ): Call<WeatherResponse>

    @GET("/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
    fun getCurrentWeather(
        @Query(value = "base_date", encoded = true) base_date: String,
        @Query(value = "base_time", encoded = true) base_time: String,
        @Query(value = "nx", encoded = true) nx: String,
        @Query(value = "ny", encoded = true) ny: String,
        @Query(value = "serviceKey", encoded = true) serviceKey: String,
        @Query(value = "dataType", encoded = true) dataType: String = "JSON",
        @Query(value = "numOfRows", encoded = true) numOfRows: String = "10",
        @Query(value = "pageNo", encoded = true) pageNo: String = "1"
    ): Call<CurrentWeatherResponse>
}