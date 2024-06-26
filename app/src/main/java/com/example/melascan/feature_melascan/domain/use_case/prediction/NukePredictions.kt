package com.example.melascan.feature_melascan.domain.use_case.prediction

import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository

class NukePredictions(
    private val repository: PredictionRepository
) {

    suspend operator fun invoke() = repository.nukeTheRepository()

}