package com.dvpermyakov.exchangerate.domain

interface CurrencyOrderRepository {
    fun getOrder(): List<CurrencyCode>
    fun setOrder(value: List<CurrencyCode>)
}
