package com.toy.weatherfit.style.repository

import com.toy.weatherfit.style.domain.UserStyle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStyleRepository : JpaRepository<UserStyle, Long> {
}