package com.example.melascan.feature_melascan.presentation.images_screen

import com.example.melascan.feature_melascan.domain.util.PredictionsOrder

sealed class ImagesEvent {
    data class Order(val predictionsOrder: PredictionsOrder) : ImagesEvent()
    object ToggleOrderSection : ImagesEvent()

    object SetPromptTrue : ImagesEvent()
}