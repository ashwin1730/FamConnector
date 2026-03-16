package com.ltimindtree.famconnector.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object MyFamily : Screen("my_family")
    object Alerts : Screen("alerts")
    object SightingForm : Screen("sighting_form")
    object SightingFeed : Screen("sighting_feed")
    object Settings : Screen("settings")
    
    object ProfileDetail : Screen("profile_detail/{profileId}") {
        fun createRoute(profileId: Long) = "profile_detail/$profileId"
    }
    
    object ProfileEditor : Screen("profile_editor/{profileId}") {
        fun createRoute(profileId: Long = -1L) = "profile_editor/$profileId"
    }
    
    object CreateAlert : Screen("create_alert/{profileId}") {
        fun createRoute(profileId: Long) = "create_alert/$profileId"
    }
}
