package com.dvpermyakov.exchangerate.domain

data class CurrencyEntity(
    val id: Int,
    val image: String,
    val code: CurrencyCode,
    val name: String
)
