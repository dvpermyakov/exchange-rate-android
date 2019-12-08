package com.dvpermyakov.exchangerate.interactions

import com.dvpermyakov.exchangerate.domain.*
import javax.inject.Inject

class GetRateList @Inject constructor(
    private val userInputValueRepository: UserInputValueRepository,
    private val currencyRepository: CurrencyRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend fun invoke(): Result {
        val userValue = userInputValueRepository.getValue()
        val currencyList = currencyRepository.getCurrencyList()
        val exchangeRateList = exchangeRateRepository.getExchangeRateList(FROM_CODE_BASE)

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
                        value = if (userValue.code == currency.code) {
                            userValue.value
                        } else {
                            exchangeRateList.findValue(userValue.value, currency.code)
                        }
                    )
                }
            )
        }
    }

    private fun List<ExchangeRateEntity>.findValue(value: Float, toCode: CurrencyCode): Float {
        return (find { exchangeRate ->
            exchangeRate.toCode == toCode
        }?.value ?: 0f) * value
    }

    sealed class Result {
        data class Success(
            val items: List<RateItem>
        ) : Result() {
            data class RateItem(
                val id: Int,
                val image: String,
                val code: CurrencyCode,
                val name: String,
                val value: Float
            )
        }

        object Empty : Result()
    }

    companion object {
        private const val FROM_CODE_BASE = "EUR"
    }
}
