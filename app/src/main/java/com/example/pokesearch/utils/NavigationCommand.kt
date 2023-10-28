package com.example.pokesearch.utils

import androidx.navigation.NavDirections

sealed class NavigationCommand {

    //navigate to a direction
    data class To(val directions: NavDirections): NavigationCommand()

    //navigate back to the previous fragment
    data object Back: NavigationCommand()

    //navigate back to a destination in the back stack
    data class BackTo(val destinationID: Int) : NavigationCommand()
}