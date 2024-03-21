package com.example.melascan.feature_melascan.domain.use_case.prediction

import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository
import com.example.melascan.feature_melascan.domain.model.InvalidPredictionException
import com.example.melascan.feature_melascan.domain.model.Prediction

class AddPrediction(
    private val repository: PredictionRepository
) {

    @Throws(InvalidPredictionException::class)
    suspend operator fun invoke(prediction: Prediction) {

        // check for a bunch of issues then throw an error if any are found :)
        Prediction.validatePrediction(prediction)

        repository.insertPrediction(prediction)
    }
}