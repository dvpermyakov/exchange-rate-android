package com.dvpermyakov.exchangerate.domain

interface ExchangeRateRepository {
    fun getExchangeRateList(): List<ExchangeRateEntity>
}
