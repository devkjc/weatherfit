package com.toy.weatherfit.style.repository

import com.toy.weatherfit.style.domain.Style
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StyleRepository : JpaRepository<Style, Long> {
}