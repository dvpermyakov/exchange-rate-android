package com.dvpermyakov.exchangerate.domain

inline class CurrencyCode(private val value: String) {
    override fun toString(): String {
        return value
    }
}
