package com.toy.weatherfit.api

import com.toy.weatherfit.feed.dto.FeedDto
import com.toy.weatherfit.feed.service.FeedFileService
import com.toy.weatherfit.feed.service.FeedService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
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

    @PostMapping(
        "/save", consumes = [
            MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE,
        ]
    )
    fun saveFeed(
        @RequestPart request: FeedDto.RequestDto,
        @RequestPart feedFiles: List<MultipartFile>,
    ) {
        feedService.saveFeed(1L, request, feedFiles)
    }

    @GetMapping
    fun getFeedList(
        @PageableDefault(size = 20, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): Page<FeedDto.ResponseDto> {
        return feedService.getFeedList(1L, pageable)
    }

}