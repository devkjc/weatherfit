package com.toy.weatherfit.feed.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class FeedFile(

    val originalFileName: String,
    val fileName: String,
    val filePath: String,
    val thumbnailPath: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}