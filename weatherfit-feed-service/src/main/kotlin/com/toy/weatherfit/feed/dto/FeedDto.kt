package com.toy.weatherfit.feed.dto

import com.toy.weatherfit.feed.domain.Feed
import com.toy.weatherfit.feed.domain.FeedFile
import com.toy.weatherfit.user.dto.UserResponse
import com.toy.weatherfit.weather.domain.WeatherAsos
import java.time.LocalDate


class FeedDto {
    data class RequestDto(
        val content: String,
        val longitude: Double,
        val latitude: Double,
        val photoDate: LocalDate
    ){
        fun toEntity(userId: Long, feedFiles: MutableList<FeedFile>): Feed {
            return Feed(
                content = content,
                userId = userId,
                longitude = longitude,
                latitude = latitude,
                photoDate = photoDate,
                feedFiles = feedFiles
            )
        }
    }

    data class ResponseDto(
        val id: Long,
        val content: String,
        val photoDate: LocalDate,
        val userDto: UserResponse,
        val feedFiles: MutableList<FeedFile>,
        val likeCount: Long,
        val isLike: Boolean,
        val weatherAsos: WeatherAsos,
    ){
        companion object{
            fun of(feed: Feed, userDto: UserResponse, likeCount: Long, isLike: Boolean, weatherAsos: WeatherAsos): ResponseDto {
                return ResponseDto(
                    id = feed.id ?: 0L,
                    content = feed.content,
                    photoDate = feed.photoDate,
                    userDto = userDto,
                    feedFiles = feed.feedFiles,
                    likeCount = likeCount,
                    isLike = isLike,
                    weatherAsos = weatherAsos
                )
            }
        }
    }
}
