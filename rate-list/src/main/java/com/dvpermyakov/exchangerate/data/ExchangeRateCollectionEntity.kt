package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.ExchangeRateEntity

data class ExchangeRateCollectionEntity(
    val fromCode: CurrencyCode,
    val list: List<ExchangeRateEntity>
)
