package com.toy.weatherfit.feed.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface FeedRepository : JpaRepository<Feed, Long> {


}