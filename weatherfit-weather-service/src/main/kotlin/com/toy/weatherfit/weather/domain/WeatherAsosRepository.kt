package com.toy.weatherfit.weather.domain

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface WeatherAsosRepository : CrudRepository<WeatherAsos, Long> {

    @Query(
        value = """SELECT DATE_FORMAT(a.dt, '%Y%m%d') AS missing_dates
                    FROM (
                        SELECT DATE_ADD('2010-10-01', INTERVAL t4*1000 + t3*100 + t2*10 + t1 DAY) AS dt
                        FROM
                            (SELECT 0 t1 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t1,
                            (SELECT 0 t2 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t2,
                            (SELECT 0 t3 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t3,
                            (SELECT 0 t4 UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9) t4
                        ) a
                    LEFT JOIN weather_asos AS w ON DATE_FORMAT(a.dt, '%Y%m%d') = w.date
                    WHERE a.dt BETWEEN '2010-10-01' AND DATE_SUB(CURDATE(), INTERVAL 1 DAY)
                    AND w.date IS NULL
                    ORDER BY missing_dates;""", nativeQuery = true
    )
    fun getMissingDates() : List<String>


    @Modifying
    @Transactional
    @Query(
        value = "update weather_asos wa\n" +
                "set stn_nm = (select obs.name from observatory obs where obs.std_no = wa.stn_no)\n" +
                "where stn_nm = 'data.stnNm'", nativeQuery = true
    )
    fun updateStnNm(): Int

    @Cacheable(
        value = ["weatherAsosCache"],
        key = "'stnNo:' + #id.stnNo + ':date:' + #id.date"
    )
    fun findById(id: WeatherAsos.WeatherAsosId): WeatherAsos?

}