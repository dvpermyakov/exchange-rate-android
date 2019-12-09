package com.dvpermyakov.exchangerate.domain

import com.dvpermyakov.exchangerate.data.ExchangeRateCollectionEntity

interface ExchangeRateRepository {
    suspend fun getExchangeRateCollection(): ExchangeRateCollectionEntity?
    suspend fun saveExchangeRateCollection(value: ExchangeRateCollectionEntity)
}
