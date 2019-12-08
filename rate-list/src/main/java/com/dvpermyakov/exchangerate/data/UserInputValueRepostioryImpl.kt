package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.UserInputValueEntity
import com.dvpermyakov.exchangerate.domain.UserInputValueRepository

class UserInputValueRepositoryImpl : UserInputValueRepository {
    override fun getValue(): UserInputValueEntity {
        return UserInputValueEntity(
            code = CurrencyCode("EUR"),
            value = 1f
        )
    }
}
