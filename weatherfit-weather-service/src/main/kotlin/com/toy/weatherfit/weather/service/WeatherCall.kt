package com.toy.weatherfit.weather.service

import com.google.gson.GsonBuilder
import com.toy.weatherfit.weather.domain.MapCodeRepository
import com.toy.weatherfit.weather.dto.CurrentWeather
import com.toy.weatherfit.weather.dto.CurrentWeatherResponse
import com.toy.weatherfit.weather.dto.WeatherResponse
import okhttp3.OkHttpClient
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
import java.util.concurrent.TimeUnit

@Service
class WeatherCall(
    private val mapCodeRepository: MapCodeRepository
) {

    @Value("\${weather.data.service-key}") // YAML 파일의 키 이름을 지정합니다.
    private lateinit var serviceKey: String

    // OkHttpClient를 생성하고 타임아웃을 설정
    private final val httpClient = OkHttpClient.Builder()
        .readTimeout(120, TimeUnit.SECONDS) // 읽기 타임아웃 (초 단위)
        .connectTimeout(120, TimeUnit.SECONDS) // 연결 타임아웃 (초 단위)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(httpClient)
        .build()

    private val apiService = retrofit.create(WeatherCallService::class.java)

    /**
     * 현재 시간의 날씨를 호출하는 함수
     * @param lat Double
     * @param lon Double
     * @return CurrentWeather?
     */
    fun callCurrentWeather(lat: Double = 37.6472744, lon: Double = 127.116003): CurrentWeather? {
        val baseDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
        val mapCode = mapCodeRepository.getMapCodeByLatLon(lat, lon)
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

    /**
     * 과거 날씨를 호출하는 함수
     * @param startDt String
     * @param endDt String
     * @param stnId Int
     * @return WeatherResponse.Response.Body.Items.Item?
     */
    fun callDailyWeather(startDt: String, endDt: String, stnId: Long?, all: Boolean): WeatherResponse.Response.Body.Items? {
        val call = apiService.getDailyWeather(
            startDt = startDt,
            endDt = endDt,
            serviceKey = serviceKey,
            numOfRows = if (all) "999" else "1",
            stnIds = stnId.toString()
        )

        val response = call.execute()
        return if (response.isSuccessful && response.body() != null && response.body()?.resultCode.equals("00")) {
            response.body()!!.getWeathers()
        } else {
            println(response)
            null
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
        @Query(value = "numOfRows", encoded = true) numOfRows: String,
        @Query(value = "serviceKey", encoded = true) serviceKey: String,
        @Query(value = "stnIds", encoded = true) stnIds: String = "108",
        @Query(value = "dataType", encoded = true) dataType: String = "JSON",
        @Query(value = "dataCd", encoded = true) dataCd: String = "ASOS",
        @Query(value = "dateCd", encoded = true) dateCd: String = "DAY"
    ): Call<WeatherResponse>

    @GET("/api/typ01/url/kma_sfcdd.php?tm=20231008&stn=0&help=1&authKey=qkoAyovRQwmKAMqL0bMJXA")
    fun getDailyWeather2(
        @Query(value = "startDt", encoded = true) startDt: String,
        @Query(value = "endDt", encoded = true) endDt: String,
        @Query(value = "numOfRows", encoded = true) numOfRows: String,
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