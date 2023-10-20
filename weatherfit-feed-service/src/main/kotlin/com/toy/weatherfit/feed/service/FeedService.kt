package com.toy.weatherfit.feed.service

import com.toy.weatherfit.feed.domain.FeedRepository
import com.toy.weatherfit.feed.dto.FeedDto
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FeedService(
    private val feedFileService: FeedFileService,
    private val feedRepository: FeedRepository
) {

    fun saveFeed(userId: Long,
                 request: FeedDto.RequestDto,
                 feedFiles : List<MultipartFile>) {
        val saveFeedFile = feedFileService.uploadFile(feedFiles)
        feedRepository.save(request.toEntity(userId, saveFeedFile))
    }

}