package com.dvpermyakov.exchangerate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvpermyakov.exchangerate.interactions.GetRateListSubscription
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class RateListViewModel @Inject constructor(
    private val getRateListSubscription: GetRateListSubscription
) : ViewModel() {

    private val job = SupervisorJob()
    private val ioScope = CoroutineScope(job + Dispatchers.IO)

    private val rateListStateMutableLiveData = MutableLiveData<RateListState>()
    private val progressBarMutableLiveData = MutableLiveData<Boolean>()

    val rateListStateLiveData: LiveData<RateListState>
        get() = rateListStateMutableLiveData

    val progressBarLiveData: LiveData<Boolean>
        get() = progressBarMutableLiveData

    init {
        viewModelScope.launch {
            progressBarMutableLiveData.postValue(true)
        }

        ioScope.launch {
            getRateListSubscription.invoke().collect { result ->
                when (result) {
                    is GetRateListSubscription.Result.Success -> {
                        viewModelScope.launch {
                            progressBarMutableLiveData.postValue(false)
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
        }
    }

    override fun onCleared() {
        job.cancel()
    }

    fun onRateItemClick(rateId: String) {
    }
}
