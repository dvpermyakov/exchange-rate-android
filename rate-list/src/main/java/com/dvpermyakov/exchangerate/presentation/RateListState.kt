package com.dvpermyakov.exchangerate.presentation

data class RateListState(
    val items: List<RateItem>
) {
    data class RateItem(
        val id: Int,
        val image: Int,
        val code: String,
        val name: String
    )
}
