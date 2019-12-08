package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.CurrencyEntity
import com.dvpermyakov.exchangerate.domain.CurrencyRepository

class CurrencyRepositoryImpl : CurrencyRepository {
    override fun getCurrencyList(): List<CurrencyEntity> {
        return listOf(
            CurrencyEntity(
                id = 1,
                image = "file:///android_asset/euro.png",
                code = CurrencyCode("EUR"),
                name = "Евро"
            ),
            CurrencyEntity(
                id = 2,
                image = "file:///android_asset/rur.png",
                code = CurrencyCode("RUB"),
                name = "Рубль"
            ),
            CurrencyEntity(
                id = 3,
                image = "file:///android_asset/usa.png",
                code = CurrencyCode("USD"),
                name = "Доллар"
            )
        )
    }
}
