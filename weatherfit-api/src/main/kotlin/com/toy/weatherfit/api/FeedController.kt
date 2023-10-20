package com.toy.weatherfit.api

import com.toy.weatherfit.feed.dto.FeedDto
import com.toy.weatherfit.feed.service.FeedFileService
import com.toy.weatherfit.feed.service.FeedService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RequestMapping("/feed")
@RestController
class FeedController(
    private val feedService: FeedService,
    private val feedFileService: FeedFileService
) {

    @PostMapping("/save", consumes = [
        MediaType.MULTIPART_FORM_DATA_VALUE,
        MediaType.APPLICATION_JSON_VALUE,
    ])
    fun saveFeed(
        @RequestPart request: FeedDto.RequestDto,
        @RequestPart feedFiles: List<MultipartFile>,
    ) {
        feedService.saveFeed(1L, request, feedFiles)
    }

}