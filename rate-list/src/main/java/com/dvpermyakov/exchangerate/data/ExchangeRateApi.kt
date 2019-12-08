package com.dvpermyakov.exchangerate.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {
    @GET("/latest")
    suspend fun getLatestExchangeRates(
        @Query("base") base: String
    ): LatestExchangeRatesDto

    @Serializable
    data class LatestExchangeRatesDto(
        @SerialName("base")
        val base: String,
        @SerialName("date")
        val date: String,
        @SerialName("rates")
        val rates: Map<String, Float>
    )
}
