package com.dvpermyakov.exchangerate.interactions

import com.dvpermyakov.exchangerate.domain.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRateListSubscription @Inject constructor(
    private val userInputValueRepository: UserInputValueRepository,
    private val currencyRepository: CurrencyRepository,
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend fun invoke(): Flow<Result> = flow {
        while (true) {

            val userValue = userInputValueRepository.getValue()
            val currencyList = currencyRepository.getCurrencyList()
            val exchangeRateList = exchangeRateRepository.getExchangeRateList(
                fromCode = userValue.code.toString()
            )

            emit(if (currencyList.isEmpty() || exchangeRateList.isEmpty()) {
                Result.Empty
            } else {
                val items = currencyList
                    .map { currency ->
                        Result.Success.RateItem(
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

                val selectedRateItem: Result.Success.RateItem = items.first { rateItem ->
                    rateItem.code == userValue.code
                }

                val mutableItems = items.toMutableList()
                mutableItems.remove(selectedRateItem)
                mutableItems.add(0, selectedRateItem)

                Result.Success(items = mutableItems)
            })

            delay(1000)
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
                val image: String,
                val code: CurrencyCode,
                val name: String,
                val value: Float
            )
        }

        object Empty : Result()
    }
}
