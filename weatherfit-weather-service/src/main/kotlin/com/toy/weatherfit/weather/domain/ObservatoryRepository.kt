package com.toy.weatherfit.weather.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ObservatoryRepository: JpaRepository<Observatory, Long> {

    @Query(
        value = """
            SELECT *,
                   ST_DISTANCE(POINT(:lon, :lat), POINT(longitude, latitude)) AS distance
            FROM observatory
            ORDER BY distance
            LIMIT 1
        """, nativeQuery = true
    )
    fun getObservatoryByLatLon(@Param("lat") lat: Double, @Param("lon") lon: Double): Observatory

}