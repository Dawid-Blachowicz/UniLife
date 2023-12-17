package com.blach.unilife.ui.navigation

sealed class NavigationCommand {
    data class NavigateTo(val destination: String): NavigationCommand()
}
