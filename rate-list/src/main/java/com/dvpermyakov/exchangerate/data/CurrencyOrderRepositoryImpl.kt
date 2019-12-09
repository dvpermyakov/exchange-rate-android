package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.CurrencyOrderRepository

class CurrencyOrderRepositoryImpl : CurrencyOrderRepository {
    private var value: List<CurrencyCode> = emptyList()

    override fun getOrder(): List<CurrencyCode> {
        return value
    }

    override fun setOrder(value: List<CurrencyCode>) {
        this.value = value
    }

}
