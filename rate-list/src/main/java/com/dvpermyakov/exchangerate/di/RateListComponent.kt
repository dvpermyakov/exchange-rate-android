package com.dvpermyakov.exchangerate.di

import com.dvpermyakov.exchangerate.presentation.RateListViewModel
import dagger.Component
import dagger.Module

@Component(modules = [RateListModule::class])
interface RateListComponent {

    fun inject(): RateListViewModel

    @Component.Factory
    interface Factory {
        fun create(): RateListComponent
    }
}


@Module
class RateListModule
