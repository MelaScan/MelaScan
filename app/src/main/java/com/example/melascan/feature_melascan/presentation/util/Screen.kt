package com.example.melascan.feature_melascan.presentation.util

sealed class Screen(val route: String) {
    data object HomeScreen: Screen("home_screen")
    data object ImageScreen: Screen("images_screen")

    data object CompanyScreen: Screen("company_screen")

    object ImagePromptScreens {
        data object One : Screen("img_prompt_one")
    }

    data object TakePhoto: Screen("photo_screen")

    data object DataInput: Screen("data_input")

    data object PredictionScreen: Screen("prediction_screen")

    data object ImageViewScreen: Screen("image_screen") // this is a cheap fix

    data object ImageSelectScreen: Screen("image_select_screen")
}