package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository

class ExchangeRateRepositoryImpl : ExchangeRateRepository {
    private var value: ExchangeRateCollectionEntity? = null

    override suspend fun getExchangeRateCollection(): ExchangeRateCollectionEntity? {
        return value
    }

    override suspend fun saveExchangeRateCollection(value: ExchangeRateCollectionEntity) {
        this.value = value
    }
}
