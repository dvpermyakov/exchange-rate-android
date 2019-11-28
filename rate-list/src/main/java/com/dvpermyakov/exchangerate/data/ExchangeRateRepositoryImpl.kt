package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.ExchangeRateEntity
import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository

class ExchangeRateRepositoryImpl : ExchangeRateRepository {
    override fun getExchangeRateList(): List<ExchangeRateEntity> {
        return listOf(
            ExchangeRateEntity(
                fromCode = "EUR",
                toCode = "RUR",
                value = 0.213f
            ),
            ExchangeRateEntity(
                fromCode = "EUR",
                toCode = "RUR",
                value = 0.213f
            )
        )
    }
}
