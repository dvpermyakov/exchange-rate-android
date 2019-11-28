package com.dvpermyakov.exchangerate.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class RateListViewModel @Inject constructor() : ViewModel() {

    private val rateListStateMutableLiveData = MutableLiveData<RateListState>()

    val rateListStateLiveData: LiveData<RateListState>
        get() {
            return rateListStateMutableLiveData
        }

    init {
        rateListStateMutableLiveData.value = RateListState(
            items = listOf(
                RateListState.RateItem(
                    id = 1,
                    image = 0,
                    code = "RUR",
                    name = "Рубль"
                ),
                RateListState.RateItem(
                    id = 2,
                    image = 0,
                    code = "RUR",
                    name = "Рубль"
                ),
                RateListState.RateItem(
                    id = 3,
                    image = 0,
                    code = "RUR",
                    name = "Рубль"
                )
            )
        )
    }

    fun onRateItemClick(rateId: Int) {
    }
}
