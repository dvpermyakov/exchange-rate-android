package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.ExchangeRateEntity
import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository

class ExchangeRateRepositoryImpl(
    private val api: ExchangeRateApi
) : ExchangeRateRepository {

    override suspend fun getExchangeRateList(fromCode: String): List<ExchangeRateEntity> {
        return api.getLatestExchangeRates(fromCode).rates.map { rateMapEntry ->
            ExchangeRateEntity(
                fromCode = CurrencyCode(fromCode),
                toCode = CurrencyCode(rateMapEntry.key),
                value = rateMapEntry.value
            )
        }
    }
}
