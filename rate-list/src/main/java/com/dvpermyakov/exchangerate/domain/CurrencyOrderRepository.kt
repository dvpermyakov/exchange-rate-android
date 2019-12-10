package com.dvpermyakov.exchangerate.domain

interface CurrencyOrderRepository {
    suspend fun getOrder(): List<CurrencyCode>
    suspend fun setOrder(value: List<CurrencyCode>)
}
