package com.dvpermyakov.exchangerate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.interactions.ChangeUserInputValue
import com.dvpermyakov.exchangerate.interactions.GetRateListSubscription
import com.dvpermyakov.exchangerate.interactions.SetCurrencyCodeFirst
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectIndexed
import javax.inject.Inject

class RateListViewModel @Inject constructor(
    private val getRateListSubscription: GetRateListSubscription,
    private val changeUserInputValue: ChangeUserInputValue,
    private val setCurrencyCodeFirst: SetCurrencyCodeFirst
) : ViewModel() {

    private var subscriptionJob: Job? = null

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(job + Dispatchers.IO)

    private val rateListStateMutableLiveData = MutableLiveData<RateListState>()
    private val progressBarMutableLiveData = MutableLiveData<Boolean>()
    private val scrollToFirstPositionMutableLiveData = MutableLiveData<Boolean>()

    val rateListStateLiveData: LiveData<RateListState>
        get() = rateListStateMutableLiveData
    val progressBarLiveData: LiveData<Boolean>
        get() = progressBarMutableLiveData
    val scrollToFirstPositionLiveData: LiveData<Boolean>
        get() = scrollToFirstPositionMutableLiveData

    init {
        viewModelScope.launch {
            progressBarMutableLiveData.postValue(true)
        }

        subscriptionJob = ioScope.launch {
            subscribeToRateList(firstSuccessBlock = {
                viewModelScope.launch {
                    progressBarMutableLiveData.postValue(false)
                }
            })
        }
    }

    override fun onCleared() {
        job.cancel()
    }

    fun onValueChange(value: String) {
        ioScope.launch {
            try {
                changeUserInputValue.invoke(
                    value = value.toFloat()
                )
            } catch (ignore: NumberFormatException) {
                changeUserInputValue.invoke(value = 0f)
            }
        }
        subscriptionJob?.cancel()
        subscriptionJob = ioScope.launch {
            subscribeToRateList(firstSuccessBlock = {})
        }
    }

    fun onRateItemClick(rateId: String, value: String) {
        ioScope.launch {
            changeUserInputValue.invoke(
                currencyCode = CurrencyCode(rateId),
                value = value.toFloat()
            )
            setCurrencyCodeFirst.invoke(
                code = CurrencyCode(rateId)
            )
        }
        subscriptionJob?.cancel()
        subscriptionJob = ioScope.launch {
            subscribeToRateList(firstSuccessBlock = {
                viewModelScope.launch {
                    scrollToFirstPositionMutableLiveData.postValue(true)
                }
            })
        }
    }

    private suspend inline fun subscribeToRateList(crossinline firstSuccessBlock: () -> Unit) {
        getRateListSubscription.invoke().collectIndexed { index, result ->
            if (index == 0) {
                firstSuccessBlock()
            }
            viewModelScope.launch {
                rateListStateMutableLiveData.postValue(RateListState(
                    items = result.items.map { item ->
                        RateListState.RateItem(
                            id = item.code.toString(),
                            image = item.image,
                            name = item.name,
                            code = item.code.toString(),
                            value = String.format("%.2f", item.value)
                        )
                    }
                ))
            }
        }
    }
}
