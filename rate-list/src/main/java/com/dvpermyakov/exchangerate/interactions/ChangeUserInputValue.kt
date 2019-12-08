package com.dvpermyakov.exchangerate.interactions

import com.dvpermyakov.exchangerate.domain.CurrencyCode
import com.dvpermyakov.exchangerate.domain.UserInputValueEntity
import com.dvpermyakov.exchangerate.domain.UserInputValueRepository
import javax.inject.Inject

class ChangeUserInputValue @Inject constructor(
    private val userInputValueRepository: UserInputValueRepository
) {
    suspend fun invoke(currencyCode: CurrencyCode, value: Float) {
        userInputValueRepository.setValue(
            UserInputValueEntity(
                code = currencyCode,
                value = value
            )
        )
    }
}
