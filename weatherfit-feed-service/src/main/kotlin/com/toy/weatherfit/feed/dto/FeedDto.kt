package com.toy.weatherfit.feed.dto

import com.toy.weatherfit.feed.domain.Feed
import com.toy.weatherfit.feed.domain.FeedFile
import org.springframework.web.multipart.MultipartHttpServletRequest


class FeedDto {
    data class RequestDto(
        val content: String,
        val longitude: Double,
        val latitude: Double,
    ){
        fun toEntity(userId: Long, feedFiles: MutableList<FeedFile>): Feed {
            return Feed(
                content = content,
                userId = userId,
                longitude = longitude,
                latitude = latitude,
                feedFiles = feedFiles
            )
        }
    }

    data class ResponseDto(
        val content: String,
        val userId: Long,
        val feedFiles: MutableList<FeedFile>,
    ){

    }
}
