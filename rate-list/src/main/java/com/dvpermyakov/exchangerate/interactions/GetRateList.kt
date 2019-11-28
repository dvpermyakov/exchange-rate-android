package com.dvpermyakov.exchangerate.interactions

import com.dvpermyakov.exchangerate.domain.CurrencyRepository
import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository
import javax.inject.Inject

class GetRateList @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) {
    fun invoke(): Result {
        val currencyList = currencyRepository.getCurrencyList()
        val exchangeRateList = exchangeRateRepository.getExchangeRateList()

        return if (currencyList.isEmpty() || exchangeRateList.isEmpty()) {
            Result.Empty
        } else {
            Result.Success(
                items = currencyList.map { currency ->
                    Result.Success.RateItem(
                        id = currency.id,
                        image = currency.image,
                        code = currency.code,
                        name = currency.name,
                        value = getValue()
                    )
                }
            )
        }
    }

    // todo: get value from exchangeRateList
    private fun getValue(): Float {
        return 0f
    }

    sealed class Result {
        data class Success(
            val items: List<RateItem>
        ) : Result() {
            data class RateItem(
                val id: Int,
                val image: String,
                val code: String,
                val name: String,
                val value: Float
            )
        }

        object Empty : Result()
    }
}
