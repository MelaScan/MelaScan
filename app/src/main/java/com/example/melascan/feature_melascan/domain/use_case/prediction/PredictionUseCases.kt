package com.example.melascan.feature_melascan.domain.use_case.prediction

data class PredictionUseCases(
    val addPrediction: AddPrediction,
    val deletePrediction: DeletePrediction,
    val getPrediction: GetPrediction,
    val getPredictions: GetPredictions,
    val nukePredictions: NukePredictions,
    val getLatestPrediction: GetLatestPrediction
)