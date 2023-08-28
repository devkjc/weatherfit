package com.toy.style.repository

import com.toy.style.domain.UserStyle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStyleRepository : JpaRepository<UserStyle, Long> {
}