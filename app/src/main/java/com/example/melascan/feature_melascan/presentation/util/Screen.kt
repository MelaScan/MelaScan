package com.example.melascan.feature_melascan.presentation.util

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object ImageScreen: Screen("image_screen")

    object CompanyScreen: Screen("company_screen")

    object ImagePromptScreens {
        object One : Screen("img_prompt_one")
    }

    object TakePhoto: Screen("photo_screen")

    object DataInput: Screen("data_input")
}