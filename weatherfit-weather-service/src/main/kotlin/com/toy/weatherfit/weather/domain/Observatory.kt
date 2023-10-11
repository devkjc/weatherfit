package com.toy.weatherfit.weather.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Observatory(
    val name: String,
    val latitude: Double,
    val longitude: Double,

    @Id
    val stdNo: Long,
)