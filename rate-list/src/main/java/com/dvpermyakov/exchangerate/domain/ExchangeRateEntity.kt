package com.dvpermyakov.exchangerate.domain

data class ExchangeRateEntity(
    val fromCode: String,
    val toCode: String,
    val value: Float
)
