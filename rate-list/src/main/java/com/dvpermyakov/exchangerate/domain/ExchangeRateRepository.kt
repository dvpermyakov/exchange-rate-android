package com.dvpermyakov.exchangerate.domain

interface ExchangeRateRepository {
    suspend fun getExchangeRateList(fromCode: String): List<ExchangeRateEntity>
}
