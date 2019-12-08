package com.dvpermyakov.exchangerate.data

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.UserInputValueEntity
import com.dvpermyakov.exchangerate.domain.UserInputValueRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class UserInputValueRepositoryImpl : UserInputValueRepository {
    private val mutex = Mutex()

    private var userInput: UserInputValueEntity = UserInputValueEntity(
        code = CurrencyCode("EUR"),
        value = 100f
    )

    override suspend fun getValue(): UserInputValueEntity {
        return mutex.withLock {
            userInput
        }
    }

    override suspend fun setValue(value: UserInputValueEntity) {
        mutex.withLock {
            userInput = value
        }
    }
}
