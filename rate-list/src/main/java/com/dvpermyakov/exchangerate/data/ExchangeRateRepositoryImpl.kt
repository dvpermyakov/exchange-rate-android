package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ExchangeRateRepositoryImpl : ExchangeRateRepository {
    private val mutex = Mutex()

    private var value: ExchangeRateCollectionEntity? = null

    override suspend fun getExchangeRateCollection(): ExchangeRateCollectionEntity? {
        return mutex.withLock {
            value
        }
    }

    override suspend fun saveExchangeRateCollection(value: ExchangeRateCollectionEntity) {
        mutex.withLock {
            this.value = value
        }
    }
}
