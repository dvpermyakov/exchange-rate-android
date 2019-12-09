package com.dvpermyakov.exchangerate.domain

interface ExchangeRateGateway {
    suspend fun getExchangeRateList(fromCode: String): List<ExchangeRateEntity>
}
