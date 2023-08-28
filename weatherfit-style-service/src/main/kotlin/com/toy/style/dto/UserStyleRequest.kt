package com.toy.style.dto

import jakarta.validation.constraints.Size

data class UserStyleRequest(
    val userId : Long,
    @field:Size(min = 1)
    val styleIdList : List<Long>
)
