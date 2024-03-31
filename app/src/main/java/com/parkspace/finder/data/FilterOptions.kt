package com.parkspace.finder.data

/**
 * Data class representing filter options for parking spaces.
 * @property sortingOption The sorting option for parking spaces.
 * @property sortingOrder The sorting order for parking spaces.
 * @property distance The maximum distance for parking spaces.
 * @property duration The minimum duration for parking spaces.
 * @property rating The minimum rating for parking spaces.
 * @property priceRange The price range for parking spaces.
 */
data class FilterOptions(
    val sortingOption: String = "",
    val sortingOrder: String = "ASCE",
    val distance: String = "Any",
    val duration: String = "More than 2 hours",
    val rating: Int = 0,
    val priceRange: ClosedFloatingPointRange<Float> = 0f..100f
)