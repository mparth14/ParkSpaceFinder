package com.parkspace.finder.ui.onboarding

/*
 * This file contains the definition of the OnboardingPage sealed class and its subclasses.
 */
import androidx.annotation.DrawableRes
import com.parkspace.finder.R

/*
 * Sealed class representing different onboarding pages.
 */
sealed class OnboardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object FirstPage : OnboardingPage(
        image = R.drawable.find_nearby_parking,
        title = "Find Parking Spaces",
        description = "Find parking spaces near you with ease"
    )

    object SecondPage : OnboardingPage(
        image = R.drawable.book,
        title = "Book Parking Spaces",
        description = "Book and reserve parking spaces in advance"
    )

    object ThirdPage : OnboardingPage(
        image = R.drawable.extend_time,
        title = "Extend Bookings",
        description = "Extend your booking if you need more time"
    )
}