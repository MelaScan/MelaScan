package com.example.melascan.feature_melascan.presentation.data_screen

import com.example.melascan.feature_melascan.domain.AI.ModelSizes
import com.example.melascan.feature_melascan.domain.model.BodyLocation

data class DataState(
    val age: Int? = null,
    val bodyLocation: BodyLocation = BodyLocation.Torso,
    val modelSizes: ModelSizes = ModelSizes.Balanced
)
