package com.toy.style.domain

import jakarta.persistence.*

class UserStyle (

    val userId: Long,

    @OneToMany(mappedBy = "id", cascade = [CascadeType.ALL], orphanRemoval = true)
    val styleList: List<Style> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}