package com.toy.weatherfit.feed.service

import com.toy.weatherfit.test.DataServiceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@DataServiceTest
class FeedFileServiceTest(
    private val feedFileService: FeedFileService
) {

    @Test
    fun getRandomFileName() {

        val extension = "jpg"
        val randomFileName = feedFileService.getRandomFileName(extension)

        println(randomFileName)

        assertThat(randomFileName.length).isEqualTo(20+extension.length+1)

    }
}