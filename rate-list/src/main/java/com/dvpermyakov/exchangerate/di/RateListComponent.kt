package com.dvpermyakov.exchangerate.di

import com.dvpermyakov.exchangerate.data.CurrencyRepositoryImpl
import com.dvpermyakov.exchangerate.data.ExchangeRateRepositoryImpl
import com.dvpermyakov.exchangerate.domain.CurrencyRepository
import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository
import com.dvpermyakov.exchangerate.presentation.RateListViewModel
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [RateListModule::class])
interface RateListComponent {

    fun inject(): RateListViewModel

    @Component.Factory
    interface Factory {
        fun create(): RateListComponent
    }
}


@Module
class RateListModule {
    @Provides
    fun getCurrencyRepository(): CurrencyRepository {
        return CurrencyRepositoryImpl()
    }

    @Provides
    fun getExchangeRateRepository(): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl()
    }
}
