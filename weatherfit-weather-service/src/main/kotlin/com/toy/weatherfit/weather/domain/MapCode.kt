package com.toy.weatherfit.weather.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class MapCode(
    val code: String,
    val city: String,
    val gu: String,
    val dong: String,
    val x: Int,
    val y: Int,
    val longitude: Double,
    val latitude: Double,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}