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
            ),
            CurrencyEntity(
                id = 4,
                image = "file:///android_asset/chf.png",
                code = CurrencyCode("CHF"),
                name = "Швейцарский франк"
            ),
            CurrencyEntity(
                id = 5,
                image = "file:///android_asset/sek.png",
                code = CurrencyCode("SEK"),
                name = "Шведская крона"
            ),
            CurrencyEntity(
                id = 6,
                image = "file:///android_asset/jpy.png",
                code = CurrencyCode("JPY"),
                name = "Иена"
            ),
            CurrencyEntity(
                id = 7,
                image = "file:///android_asset/czk.png",
                code = CurrencyCode("CZK"),
                name = "Чешская крона"
            ),
            CurrencyEntity(
                id = 8,
                image = "file:///android_asset/thb.png",
                code = CurrencyCode("THB"),
                name = "Тайский бат"
            ),
            CurrencyEntity(
                id = 9,
                image = "file:///android_asset/aud.png",
                code = CurrencyCode("AUD"),
                name = "Австралийский доллар"
            )
        )
    }
}
