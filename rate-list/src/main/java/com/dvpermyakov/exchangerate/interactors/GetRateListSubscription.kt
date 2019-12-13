package com.dvpermyakov.exchangerate.interactors

import com.dvpermyakov.exchangerate.data.ExchangeRateCollectionEntity
import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.ExchangeRateGateway
import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository
import com.dvpermyakov.exchangerate.domain.UserInputValueRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRateListSubscription @Inject constructor(
    private val computeExchangeRateList: ComputeExchangeRateList,
    private val userInputValueRepository: UserInputValueRepository,
    private val exchangeRateGateway: ExchangeRateGateway,
    private val exchangeRateRepository: ExchangeRateRepository
) {
    fun invoke(): Flow<Value> = flow {
        while (true) {
            when (val result = computeExchangeRateList.invoke()) {
                is ComputeExchangeRateList.Result.Value -> {
                    emit(Value(items = result.items.map { item ->
                        Value.RateItem(
                            image = item.image,
                            code = item.code,
                            name = item.name,
                            value = item.value
                        )
                    }))
                }
                is ComputeExchangeRateList.Result.Empty -> {
                }
            }

            val userValue = userInputValueRepository.getValue()
            when (val result = exchangeRateGateway.getExchangeRateList(
                fromCode = userValue.code.toString()
            )) {
                is ExchangeRateGateway.Response.Success -> {
                    exchangeRateRepository.saveExchangeRateCollection(
                        ExchangeRateCollectionEntity(
                            fromCode = userValue.code,
                            list = result.list
                        )
                    )
                }
                is ExchangeRateGateway.Response.Failure -> {
                }
            }

            delay(1000)
        }
    }


    data class Value(
        val items: List<RateItem>
    ) {
        data class RateItem(
            val image: String,
            val code: CurrencyCode,
            val name: String,
            val value: Double
        )
    }
}
