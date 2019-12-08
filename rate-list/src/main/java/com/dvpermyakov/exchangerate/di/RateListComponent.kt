package com.dvpermyakov.exchangerate.di

import com.dvpermyakov.exchangerate.data.CurrencyRepositoryImpl
import com.dvpermyakov.exchangerate.data.ExchangeRateApi
import com.dvpermyakov.exchangerate.data.ExchangeRateRepositoryImpl
import com.dvpermyakov.exchangerate.data.UserInputValueRepositoryImpl
import com.dvpermyakov.exchangerate.domain.CurrencyRepository
import com.dvpermyakov.exchangerate.domain.ExchangeRateRepository
import com.dvpermyakov.exchangerate.domain.UserInputValueRepository
import com.dvpermyakov.exchangerate.presentation.RateListViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

@ScreenScope
@Component(modules = [RateListModule::class, NetworkModule::class])
interface RateListComponent {

    fun inject(): RateListViewModel

    @Component.Factory
    interface Factory {
        fun create(): RateListComponent
    }
}

@Module
class NetworkModule {
    @Provides
    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun getRetrofit(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit
            .Builder()
            .baseUrl("https://revolut.duckdns.org/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(client)
            .build()
    }
}

@Module
class RateListModule {
    @ScreenScope
    @Provides
    fun getUserInputValueRepository(): UserInputValueRepository {
        return UserInputValueRepositoryImpl()
    }

    @ScreenScope
    @Provides
    fun getCurrencyRepository(): CurrencyRepository {
        return CurrencyRepositoryImpl()
    }

    @ScreenScope
    @Provides
    fun getExchangeRateRepository(api: ExchangeRateApi): ExchangeRateRepository {
        return ExchangeRateRepositoryImpl(api)
    }

    @ScreenScope
    @Provides
    fun getExchangeRateApi(retrofit: Retrofit): ExchangeRateApi {
        return retrofit.create(ExchangeRateApi::class.java)
    }
}
