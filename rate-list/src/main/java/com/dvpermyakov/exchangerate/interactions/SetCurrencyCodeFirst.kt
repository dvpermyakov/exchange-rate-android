package com.dvpermyakov.exchangerate.interactions

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.CurrencyOrderRepository
import javax.inject.Inject

class SetCurrencyCodeFirst @Inject constructor(
    private val repository: CurrencyOrderRepository
) {
    fun invoke(code: CurrencyCode) {
        val order = repository.getOrder()
        val filteredOrder = order.filter { currencyCode -> currencyCode != code }

        val mutableOrder = filteredOrder.toMutableList()
        mutableOrder.add(0, code)

        repository.setOrder(mutableOrder)
    }
}
