package com.parkspace.finder.data


data class FilterOptions(
    val sortingOption: String = "",
    val sortingOrder: String = "ASCE",
    val distance: String = "Any",
    val duration: String = "More than 2 hours",
    val rating: Int = 0,
    val priceRange: ClosedFloatingPointRange<Float> = 0f..100f
)