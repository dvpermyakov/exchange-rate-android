package com.dvpermyakov.exchangerate.interactors

import com.dvpermyakov.exchangerate.domain.*
import javax.inject.Inject

class ComputeExchangeRateList @Inject constructor(
    private val currencyOrderRepository: CurrencyOrderRepository,
    private val userInputValueRepository: UserInputValueRepository,
    private val currencyRepository: CurrencyRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend fun invoke(): Result {
        val currencyOrder = currencyOrderRepository.getOrder()
        val userValue = userInputValueRepository.getValue()
        val currencyList = currencyRepository.getCurrencyList()
        val exchangeRateCollection = exchangeRateRepository.getExchangeRateCollection()

        return if (currencyList.isNotEmpty() && exchangeRateCollection != null) {
            val exchangeRateList = exchangeRateCollection.list

            val items = currencyList
                .map { currency ->
                    Result.Value.RateItem(
                        image = currency.image,
                        code = currency.code,
                        name = currency.name,
                        value = if (userValue.code == currency.code) {
                            userValue.value
                        } else {
                            if (exchangeRateCollection.fromCode != userValue.code) {
                                if (exchangeRateCollection.fromCode == currency.code) {
                                    userValue.value / exchangeRateList.findValue(userValue.code)
                                } else {
                                    exchangeRateList.findValue(currency.code) * userValue.value / exchangeRateList.findValue(
                                        userValue.code
                                    )
                                }
                            } else {
                                exchangeRateList.findValue(currency.code) * userValue.value
                            }
                        }
                    )
                }

            val mutableItems = items.toMutableList()
            currencyOrder.asReversed().forEach { currencyCode ->
                val selectedRateItem: Result.Value.RateItem =
                    items.first { rateItem ->
                        rateItem.code == currencyCode
                    }
                mutableItems.remove(selectedRateItem)
                mutableItems.add(0, selectedRateItem)
            }

            Result.Value(items = mutableItems)
        } else {
            Result.Empty
        }
    }

    private fun List<ExchangeRateEntity>.findValue(toCode: CurrencyCode): Float {
        return find { exchangeRate -> exchangeRate.toCode == toCode }?.value ?: 0f
    }

    sealed class Result {
        data class Value(
            val items: List<RateItem>
        ) : Result() {
            data class RateItem(
                val image: String,
                val code: CurrencyCode,
                val name: String,
                val value: Float
            )
        }

        object Empty : Result()
    }
}
