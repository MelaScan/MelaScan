package com.example.melascan.feature_melascan.presentation.images_screen

import android.graphics.Bitmap
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.feature_melascan.domain.model.Prompts
import com.example.melascan.feature_melascan.domain.util.OrderType
import com.example.melascan.feature_melascan.domain.util.PredictionsOrder

data class ImagesState(
    val predictions: List<Prediction> = emptyList(),
    val prompts: Prompts = Prompts(),
    val predictionsOrder: PredictionsOrder = PredictionsOrder.Date(OrderType.Descending),
    // track if order buttons are visible
    val isOrderSectionVisible: Boolean = false,
    val bitmaps: Map<Int, Bitmap> = emptyMap()
)