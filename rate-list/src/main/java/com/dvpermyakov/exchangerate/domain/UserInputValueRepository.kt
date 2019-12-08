package com.dvpermyakov.exchangerate.domain

interface UserInputValueRepository {
    suspend fun getValue(): UserInputValueEntity
    suspend fun setValue(value: UserInputValueEntity)
}
