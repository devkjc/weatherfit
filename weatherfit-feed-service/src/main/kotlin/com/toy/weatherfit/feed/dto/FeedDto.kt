package com.toy.weatherfit.feed.dto

import com.toy.weatherfit.feed.domain.Feed
import com.toy.weatherfit.feed.domain.FeedFile
import com.toy.weatherfit.user.dto.UserResponse
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
        val userDto: UserResponse,
        val feedFiles: MutableList<FeedFile>,
        val likeCount: Long,
        val isLike: Boolean
    ){
        companion object{
            fun of(feed: Feed, userDto: UserResponse, likeCount: Long, isLike: Boolean): ResponseDto {
                return ResponseDto(
                    content = feed.content,
                    userDto = userDto,
                    feedFiles = feed.feedFiles,
                    likeCount = likeCount,
                    isLike = isLike
                )
            }
        }
    }
}
