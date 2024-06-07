package com.blach.unilife.view.navigation

sealed class NavigationCommand {
    data class NavigateTo(val destination: String): NavigationCommand()
}
