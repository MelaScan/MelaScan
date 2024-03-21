package com.example.melascan.feature_melascan.domain.use_case.prediction

import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository

class GetPrediction(
    private val repository: PredictionRepository
) {
    suspend operator fun invoke(id: Int) = repository.getPredictionById(id)
}