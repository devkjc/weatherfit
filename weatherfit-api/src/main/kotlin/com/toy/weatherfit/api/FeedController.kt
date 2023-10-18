package com.toy.weatherfit.api

import com.toy.weatherfit.feed.domain.FeedFile
import com.toy.weatherfit.feed.service.FeedFileService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartHttpServletRequest

@RequestMapping("/feed")
@RestController
class FeedController(
    private val feedFileService: FeedFileService
) {

    @PostMapping("/upload")
    fun uploadFile(request: MultipartHttpServletRequest): FeedFile {
        return feedFileService.uploadFile(request)
    }

}