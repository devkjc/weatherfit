package com.toy.weatherfit.feed.domain

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class FeedLikeService(private val redisTemplate: RedisTemplate<String, String>) {

    fun likeFeed(userId: Long, feedId: Long) {
        val key = "likes:$feedId"
        redisTemplate.opsForSet().add(key, userId.toString())
    }

    fun unlikeFeed(userId: Long, feedId: Long) {
        val key = "likes:$feedId"
        redisTemplate.opsForSet().remove(key, userId.toString())
    }

    fun getLikesCount(feedId: Long): Long {
        val key = "likes:$feedId"
        return redisTemplate.opsForSet().size(key) ?: 0
    }

    fun isLikedByUser(userId: Long, feedId: Long): Boolean {
        return redisTemplate.opsForSet().isMember("likes:$feedId", userId.toString()) ?: false
    }

    fun feedLikeUserList(feedId: Long): MutableSet<String> {
        val key = "likes:$feedId"
        return redisTemplate.opsForSet().members(key) ?: mutableSetOf()
    }

}