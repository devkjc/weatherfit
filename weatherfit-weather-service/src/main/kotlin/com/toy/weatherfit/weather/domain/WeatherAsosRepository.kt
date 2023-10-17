package com.toy.weatherfit.weather.domain

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface WeatherAsosRepository : CrudRepository<WeatherAsos, Long> {

    @Modifying
    @Transactional
    @Query(value = "update weather_asos wa\n" +
            "set stn_nm = (select obs.name from observatory obs where obs.std_no = wa.stn_no)\n" +
            "where stn_nm = 'data.stnNm'", nativeQuery = true)
    fun updateStnNm(): Int

    @Cacheable(
        value = ["weatherAsosCache"],
        key = "'stnNo:' + #id.stnNo + ':date:' + #id.date"
    )
    fun findById(id: WeatherAsos.WeatherAsosId): WeatherAsos?

}