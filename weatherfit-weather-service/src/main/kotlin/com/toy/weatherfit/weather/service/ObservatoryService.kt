package com.toy.weatherfit.weather.service

import com.toy.weatherfit.weather.domain.Observatory
import com.toy.weatherfit.weather.domain.ObservatoryRepository
import org.springframework.stereotype.Service

@Service
class ObservatoryService(
    private val observatoryRepository: ObservatoryRepository
) {

    fun getAllObservatory(): MutableList<Observatory> {
        return observatoryRepository.findAll()
    }

}