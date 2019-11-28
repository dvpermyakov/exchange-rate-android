package com.dvpermyakov.exchangerate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dvpermyakov.exchangerate.di.DaggerRateListComponent

class RateListViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DaggerRateListComponent.factory()
            .create()
            .inject() as T
    }
}
