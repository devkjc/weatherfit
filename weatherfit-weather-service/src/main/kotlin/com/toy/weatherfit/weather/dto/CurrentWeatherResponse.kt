package com.toy.weatherfit.weather.dto

data class CurrentWeatherResponse(
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
    val items: Items,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)

data class Items(
    val item: List<Item>
) {

}

data class Item(
    val baseDate: String,
    val baseTime: String,
    val category: Category,
    val nx: Int,
    val ny: Int,
    val obsrValue: String
)

enum class Category(val categoryName: String, val unit: String) {
    T1H("기온", "°C"),
    RN1("1시간 강수량", "mm"),
    UUU("동서 바람 성분", "m/s"),
    VVV("남북 바람 성분", "m/s"),
    VEC("풍향", "deg"),
    REH("습도", "%"),
    PTY("강수 형태", "-"),
    WSD("풍속", "m/s"),
    ;

    enum class PTYCode(val codeName: String) {
        NONE("없음"),
        RAIN("비"),
        RAIN_SNOW("비/눈"),
        SNOW("눈"),
        SHOWER("소나기"),
        RAINDROP("빗방울"),
        RAINDROP_SNOWFALLING("빗방울눈날림"),
        SNOWFALLING("눈날림"),
        ;
        companion object {
            fun getCodeName(value: Int): String {
                return PTYCode.entries[value].codeName
            }
        }
    }


}