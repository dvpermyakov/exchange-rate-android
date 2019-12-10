package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.CurrencyOrderRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class CurrencyOrderRepositoryImpl : CurrencyOrderRepository {
    private val mutex = Mutex()

    private var value: List<CurrencyCode> = emptyList()

    override suspend fun getOrder(): List<CurrencyCode> {
        mutex.withLock {
            return value
        }
    }

    override suspend fun setOrder(value: List<CurrencyCode>) {
        mutex.withLock {
            this.value = value
        }
    }

}
