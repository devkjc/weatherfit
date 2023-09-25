package com.toy.weatherfit.weather.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MapCodeRepository : JpaRepository<MapCode, Long> {

    @Query(
        value = """
            SELECT *,
                   ST_DISTANCE(POINT(:lon, :lat), POINT(longitude, latitude)) AS distance
            FROM map_code
            ORDER BY distance
            LIMIT 1
        """, nativeQuery = true
    )
    fun getMapXY(@Param("lat") lat: Double, @Param("lon") lon: Double): MapCode

}