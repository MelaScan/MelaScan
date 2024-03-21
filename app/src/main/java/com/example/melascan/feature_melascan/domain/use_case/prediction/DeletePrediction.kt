package com.example.melascan.feature_melascan.domain.use_case.prediction

import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository
import com.example.melascan.feature_melascan.domain.model.Prediction

class DeletePrediction(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(prediction: Prediction) {
        repository.deletePrediction(prediction)
    }
}