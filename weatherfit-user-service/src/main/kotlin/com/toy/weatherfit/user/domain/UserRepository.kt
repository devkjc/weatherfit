package com.toy.weatherfit.user.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    fun countByNickName(nickName: String) : Long

}