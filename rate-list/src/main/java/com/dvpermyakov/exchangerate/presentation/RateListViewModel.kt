package com.dvpermyakov.exchangerate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dvpermyakov.exchangerate.interactions.GetRateList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class RateListViewModel @Inject constructor(
    private val getRateList: GetRateList
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
            when (val result = getRateList.invoke()) {
                is GetRateList.Result.Success -> {
                    viewModelScope.launch {
                        progressBarMutableLiveData.postValue(false)
                        rateListStateMutableLiveData.postValue(RateListState(
                            items = result.items.map { item ->
                                RateListState.RateItem(
                                    id = item.id,
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

    override fun onCleared() {
        job.cancel()
    }

    fun onRateItemClick(rateId: Int) {
    }
}
