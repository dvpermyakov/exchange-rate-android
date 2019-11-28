package com.dvpermyakov.exchangerate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dvpermyakov.exchangerate.interactions.GetRateList
import javax.inject.Inject

class RateListViewModel @Inject constructor(
    getRateList: GetRateList
) : ViewModel() {

    private val rateListStateMutableLiveData = MutableLiveData<RateListState>()

    val rateListStateLiveData: LiveData<RateListState>
        get() {
            return rateListStateMutableLiveData
        }

    init {
        when (val result = getRateList.invoke()) {
            is GetRateList.Result.Success -> {
                rateListStateMutableLiveData.value = RateListState(
                    items = result.items.map { item ->
                        mapRateItem(item)
                    }
                )
            }
        }
    }

    fun onRateItemClick(rateId: Int) {
    }

    private fun mapRateItem(item: GetRateList.Result.Success.RateItem): RateListState.RateItem {
        return RateListState.RateItem(
            id = item.id,
            image = item.image,
            name = item.name,
            code = item.code,
            value = item.value
        )
    }
}
