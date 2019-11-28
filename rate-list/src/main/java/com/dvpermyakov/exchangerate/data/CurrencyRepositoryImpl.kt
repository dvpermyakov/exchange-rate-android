package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyEntity
import com.dvpermyakov.exchangerate.domain.CurrencyRepository

class CurrencyRepositoryImpl : CurrencyRepository {
    override fun getCurrencyList(): List<CurrencyEntity> {
        return listOf(
            CurrencyEntity(
                id = 1,
                image = "file:///android_asset/rur.png",
                code = "RUR",
                name = "Рубль"
            ),
            CurrencyEntity(
                id = 2,
                image = "file:///android_asset/euro.png",
                code = "EUR",
                name = "Евро"
            ),
            CurrencyEntity(
                id = 3,
                image = "file:///android_asset/usa.png",
                code = "USD",
                name = "Доллар"
            )
        )
    }
}
