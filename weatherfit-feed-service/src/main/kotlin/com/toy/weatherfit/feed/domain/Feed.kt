package com.toy.weatherfit.feed.domain

import com.toy.weatherfit.weather.dto.CurrentWeather
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
class Feed(

    @Column(columnDefinition = "TEXT")
    val content: String,

    val userId: Long,

    val longitude: Double,

    val latitude: Double,

    val photoDate: LocalDate,

    @OneToMany
    val feedFiles: MutableList<FeedFile>,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {

    fun getPhotoDateString() : String {
        return photoDate.format(DateTimeFormatter.BASIC_ISO_DATE)
    }

}