package com.dvpermyakov.exchangerate.interactors

import com.dvpermyakov.exchangerate.data.ExchangeRateCollectionEntity
import com.dvpermyakov.exchangerate.domain.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRateListSubscription @Inject constructor(
    private val currencyOrderRepository: CurrencyOrderRepository,
    private val userInputValueRepository: UserInputValueRepository,
    private val currencyRepository: CurrencyRepository,
    private val exchangeRateGateway: ExchangeRateGateway,
    private val exchangeRateRepository: ExchangeRateRepository
) {
    suspend fun invoke(): Flow<Value> = flow {
        while (true) {

            val currencyOrder = currencyOrderRepository.getOrder()
            val userValue = userInputValueRepository.getValue()
            val currencyList = currencyRepository.getCurrencyList()
            val exchangeRateCollection = exchangeRateRepository.getExchangeRateCollection()

            if (currencyList.isNotEmpty() && exchangeRateCollection != null) {
                val exchangeRateList = exchangeRateCollection.list

                val items = currencyList
                    .map { currency ->
                        Value.RateItem(
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
                    val selectedRateItem: Value.RateItem = items.first { rateItem ->
                        rateItem.code == currencyCode
                    }
                    mutableItems.remove(selectedRateItem)
                    mutableItems.add(0, selectedRateItem)
                }

                emit(Value(items = mutableItems))
            }

            val freshExchangeRateList = exchangeRateGateway.getExchangeRateList(
                fromCode = userValue.code.toString()
            )
            exchangeRateRepository.saveExchangeRateCollection(
                ExchangeRateCollectionEntity(
                    fromCode = userValue.code,
                    list = freshExchangeRateList
                )
            )

            delay(1000)
        }
    }

    private fun List<ExchangeRateEntity>.findValue(toCode: CurrencyCode): Float {
        return find { exchangeRate -> exchangeRate.toCode == toCode }?.value ?: 0f
    }

    data class Value(
        val items: List<RateItem>
    ) {
        data class RateItem(
            val image: String,
            val code: CurrencyCode,
            val name: String,
            val value: Float
        )
    }
}