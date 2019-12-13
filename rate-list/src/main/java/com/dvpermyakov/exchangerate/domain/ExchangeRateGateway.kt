package com.dvpermyakov.exchangerate.domain

interface ExchangeRateGateway {
    suspend fun getExchangeRateList(fromCode: String): Response

    sealed class Response {
        data class Success(val list: List<ExchangeRateEntity>) : Response()
        data class Failure(val throwable: Throwable) : Response()
    }
}
