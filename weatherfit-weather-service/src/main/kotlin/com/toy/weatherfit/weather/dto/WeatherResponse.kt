package com.toy.weatherfit.weather.dto

data class WeatherResponse(
    val response: Response
) {
    data class Response(
        val header: Header,
        val body: Body
    ) {
        data class Header(
            val resultCode: String,
            val resultMsg: String
        )

        data class Body(
            val dataType: String,
            val items: Items
        ) {
            data class Items(
                val item: List<Item>
            ) {
                data class Item(
                    // 날짜
                    val tm: String,
                    // 지점 번호
                    val stnId: Long,
                    // 지점 이름
                    val stnNm: String,
                    // 평균 기온
                    val avgTa: String,
                    // 최저 기온
                    val minTa: String,
                    // 최고 기온
                    val maxTa: String,
                    // 평균 상대 습도
                    val avgRhm: String,
                    // 1시간 최다 강수량
                    val hr1MaxRn: String,
                    // 최대 풍속
                    val maxWs: String,
                    // 평균 풍속
                    val avgWs: String,
                ){
                    val formattedTm: String
                        get() = tm.replace("-", "")
                }
            }
        }
    }

    fun getWeather() = response.body.items.item.getOrNull(0)
    fun getWeathers() = response.body.items

    /**
     * 결과 코드
     */
    val resultCode: String
        get() = response.header.resultCode

    /**
     * 결과 내용
     */
    val resultMsg: String
        get() = response.header.resultMsg

    /**
     * 측정 지점명
     */
    val stnNm: String
        get() = getWeather()?.stnNm ?: ""

    /**
     * 날짜
     */
    val tm: String
        get() = getWeather()?.tm ?: ""
    /**
     * 평균 기온
     */
    val avgTa: String
        get() = getWeather()?.avgTa ?: ""

    /**
     * 최저 기온
     */
    val minTa: String
        get() = getWeather()?.minTa ?: ""


    /**
     * 최고 기온
     */
    val maxTa: String
        get() = getWeather()?.maxTa ?: ""

    /**
     * 평균 상대 습도
     */
    val avgRhm: String
        get() = getWeather()?.avgRhm ?: ""

    /**
     * 한시간 최다 강수량
     */
    val hr1MaxRn: String
        get() = getWeather()?.hr1MaxRn ?: ""

    /**
     * 최대 풍속
     */
    val maxWs: String
        get() = getWeather()?.maxWs ?: ""

    /**
     * 평균 풍속
     */
    val avgWs: String
        get() = getWeather()?.avgWs ?: ""
}