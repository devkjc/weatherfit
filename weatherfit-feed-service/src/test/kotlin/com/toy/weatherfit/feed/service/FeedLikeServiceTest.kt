package com.toy.weatherfit.feed.service

import com.toy.weatherfit.feed.domain.FeedLikeService
import com.toy.weatherfit.test.DataServiceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

@DataServiceTest
class FeedLikeServiceTest(
    private val feedLikeService: FeedLikeService
) {

    @Test
    fun likeFeedTest() {
        val userId = 1L
        val feedId = 123L
        feedLikeService.likeFeed(userId, feedId)

        val isLiked = feedLikeService.isLikedByUser(userId, feedId)
        assertThat(isLiked).isTrue()
    }

    @Test
    fun unlikeFeedTest() {
        val userId = 1L
        val feedId = 123L
        feedLikeService.likeFeed(userId, feedId)
        feedLikeService.unlikeFeed(userId, feedId)

        val isLiked = feedLikeService.isLikedByUser(userId, feedId)
        assertThat(isLiked).isFalse()
    }

    @Test
    fun getLikesCountTest() {
        val userId = 1L
        val feedId = 123L
        feedLikeService.likeFeed(userId, feedId)
        feedLikeService.likeFeed(userId, feedId)
        feedLikeService.likeFeed(2L, feedId)

        val likesCount = feedLikeService.getLikesCount(feedId)

        assertThat(likesCount).isEqualTo(2)
    }

    @Test
    fun feedLikeUserListTest() {
        val userId = 1L
        val feedId = 123L

        feedLikeService.likeFeed(userId, feedId)
        feedLikeService.likeFeed(userId, feedId)
        feedLikeService.likeFeed(2L, feedId)

        val feedLikeUserList = feedLikeService.feedLikeUserList(feedId)

        assertThat(feedLikeUserList.size).isEqualTo(2)
        assertThat(feedLikeUserList).containsExactly("1","2")
    }
}