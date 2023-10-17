package com.toy.weatherfit.feed.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Feed(

    @Column(columnDefinition = "TEXT")
    val content: String,

    val userId: Long,

    val longitude: Double,

    val latitude: Double,

    @OneToMany
    val feedFiles: MutableList<FeedFile>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}