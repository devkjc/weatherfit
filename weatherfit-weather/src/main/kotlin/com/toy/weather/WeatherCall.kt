package com.toy.weather

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherCall {


}

/*
 TODO
  날짜 관련 파라미터 관리
  지역 파라미터로 관측 지점 관리
  오류 관리
  서비스 키 관리
  코드 최적화
  첫 등록 시 시간 정보
  00시가 지난 데이터일 경우 날짜


 */
fun main() {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(WeatherCallService::class.java)

    val startDt = LocalDate.now().plusDays(4)
        .format(DateTimeFormatter.BASIC_ISO_DATE)
    val call = apiService.getDailyWeather(startDt, startDt)

    val response = call.execute()
    if (response.isSuccessful) {
        println(response)
        println(response.body())
        println(response.body()!!.response.body.items.item[0])
    } else {
        println(response)
    }
}

interface WeatherCallService {
    @GET("/1360000/AsosDalyInfoService/getWthrDataList")
    fun getDailyWeather(
        @Query(value = "startDt", encoded = true) startDt: String,
        @Query(value = "endDt", encoded = true) endDt: String,
        @Query(value= "serviceKey", encoded = true) serviceKey: String = "fVxTbyzr4UCYHaz52xRECp7FjowgGOS1%2B6JgsbFcwHODs8He8MkPqZ0SqovvcKTz%2FdY0D2BMLT0XixfKLY2K%2FQ%3D%3D",
        @Query(value= "stnIds", encoded = true) stnIds: String = "108",
        @Query(value= "dataType", encoded = true) dataType: String = "JSON",
        @Query(value= "dataCd", encoded = true) dataCd: String = "ASOS",
        @Query(value= "dateCd", encoded = true) dateCd: String = "DAY",
    ): Call<WeatherResponse>
}

data class WeatherResponse(
    val response: Response
)

data class Response(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)

data class Body(
    val dataType: String,
    val items: Items
)

data class Items(
    val item: List<Item>
)

data class Item(
    // 지점 번호
//    val stnId: String,
    // 지점 이름
    val stnNm: String,
    // 시간
//    val tm: String,
    // 평균 기온
    val avgTa: String,
    // 최저 기온
    val minTa: String,
//     최저 기온 시각
//    val minTaHrmt: String,

    // 최고 기온
    val maxTa: String,

//    // 최고 기온 시각
//    val maxTaHrmt: String,
//
//    // 10분 최다 강수량
//    val mi10MaxRn: String,
//
//    // 10분 최다 강수량 시각
//    val mi10MaxRnHrmt: String,

    // 1시간 최다 강수량
    val hr1MaxRn: String,

//    // 1시간 최다 강수량 시각
//    val hr1MaxRnHrmt: String,
//
//    // 강수 지속 시간
//    val sumRnDur: String,
//
//    // 총 강수량
//    val sumRn: String,
//
//    // 최대 순간 풍속
//    val maxInsWs: String,
//
//    // 최대 순간 풍속 풍향
//    val maxInsWsWd: String,
//
//    // 최대 순간 풍속 시각
//    val maxInsWsHrmt: String,

    // 최대 풍속
    val maxWs: String,

//    // 최대 풍속 풍향
//    val maxWsWd: String,
//
//    // 최대 풍속 시각
//    val maxWsHrmt: String,

    // 평균 풍속
    val avgWs: String,

//    // 24시간 최다 강수량
//    val hr24SumRws: String,
//
//    // 최대 풍향
//    val maxWd: String,
//
//    // 평균 이슬점 온도
//    val avgTd: String,
//
//    // 최저 상대 습도
//    val minRhm: String,
//
//    // 최저 상대 습도 시각
//    val minRhmHrmt: String,

    // 평균 상대 습도
    val avgRhm: String,

//    // 평균 증기압
//    val avgPv: String,
//
//    // 평균 현지 기압
//    val avgPa: String,
//
//    // 최대 해면 기압
//    val maxPs: String,
//
//    // 최대 해면 기압 시각
//    val maxPsHrmt: String,
//
//    // 최소 해면 기압
//    val minPs: String,
//
//    // 최소 해면 기압 시각
//    val minPsHrmt: String,
//
//    // 평균 해면 기압
//    val avgPs: String,
//
//    // 일조 시간
//    val ssDur: String,
//
//    // 총 일조 시간
//    val sumSsHr: String,
//
//    // 1시간 최다 일사 시각
//    val hr1MaxIcsrHrmt: String,
//
//    // 1시간 최다 일사량
//    val hr1MaxIcsr: String,
//
//    // 총 일사량
//    val sumGsr: String,
//
//    // 10cm 지중온도
//    val m01Te: String,
//
//    // 20cm 지중온도
//    val m02Te: String,
//
//    // 30cm 지중온도
//    val m03Te: String,
//
//    // 데이터 갱신 시각
//    val rnum: String
)