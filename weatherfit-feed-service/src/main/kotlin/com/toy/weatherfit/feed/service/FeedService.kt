package com.toy.weatherfit.feed.service

import com.toy.weatherfit.feed.domain.FeedLikeService
import com.toy.weatherfit.feed.domain.FeedRepository
import com.toy.weatherfit.feed.dto.FeedDto
import com.toy.weatherfit.user.service.UserService
import com.toy.weatherfit.weather.service.WeatherCall
import com.toy.weatherfit.weather.service.WeatherService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class FeedService(
    private val feedFileService: FeedFileService,
    private val feedLikeService: FeedLikeService,
    private val feedRepository: FeedRepository,
    private val userService: UserService,
    private val weatherCall: WeatherCall,
    private val weatherService: WeatherService
) {

    @Transactional
    fun saveFeed(userId: Long,
                 request: FeedDto.RequestDto,
                 feedFiles : List<MultipartFile>) {
        val saveFeedFile = feedFileService.uploadFile(feedFiles)

        feedRepository.save(request.toEntity(userId, saveFeedFile))
    }

    @Transactional(readOnly = true)
    fun getFeedList(userId: Long, pageable: Pageable): Page<FeedDto.ResponseDto> {
        return feedRepository.findAll(pageable).map {
            FeedDto.ResponseDto.of(
                feed = it, userDto = userService.getUser(it.userId),
                likeCount = feedLikeService.getLikesCount(it.id!!), isLike = feedLikeService.isLikedByUser(userId, it.id!!),
                weatherAsos = weatherService.getWeatherByLatAndLonAndDate(it.latitude, it.longitude, it.getPhotoDateString())
            )
        }
    }

}