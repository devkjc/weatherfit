package com.toy.style.domain

import jakarta.persistence.*

@Entity
class UserStyle (

    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "style_id")
    val style: Style,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
) {
}