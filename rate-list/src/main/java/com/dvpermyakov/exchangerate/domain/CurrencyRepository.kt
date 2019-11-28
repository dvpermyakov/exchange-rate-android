package com.dvpermyakov.exchangerate.domain

interface CurrencyRepository {
    fun getCurrencyList(): List<CurrencyEntity>
}
