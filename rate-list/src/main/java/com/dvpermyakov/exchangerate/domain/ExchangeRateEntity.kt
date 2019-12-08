package com.dvpermyakov.exchangerate.domain

data class ExchangeRateEntity(
    val fromCode: CurrencyCode,
    val toCode: CurrencyCode,
    val value: Float
)
