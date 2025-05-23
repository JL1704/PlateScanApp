package com.deltasquad.platescanapp.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera : Screen("camera")
    object Profile : Screen("profile")
    object EditProfile : Screen("editprofile")
    object Records : Screen("records")
    object Reports : Screen("reports")
    object Stats : Screen("stats")
    object Details : Screen("details/{scanId}") {
        fun createRoute(scanId: String) = "details/$scanId"
    }
    object EditData : Screen("edit_data/{scanId}") {
        fun createRoute(scanId: String) = "edit_data/$scanId"
    }
    object CreateReport : Screen("create_report")
    object DetailsReport : Screen("details_report/{reportId}") {
        fun createRoute(reportId: String) = "details_report/$reportId"
    }

}


