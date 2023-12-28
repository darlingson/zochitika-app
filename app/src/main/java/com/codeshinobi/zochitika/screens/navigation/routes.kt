package com.codeshinobi.zochitika.screens.navigation

sealed class Screens(val route : String) {
    object Home : Screens("home_route")
    object Near : Screens("near_route")
    object Profile : Screens("profile_route")
}