package com.toy.weatherfit.feed.domain

import org.springframework.data.jpa.repository.JpaRepository

interface FeedFileRepository : JpaRepository<FeedFile, Long> {
}