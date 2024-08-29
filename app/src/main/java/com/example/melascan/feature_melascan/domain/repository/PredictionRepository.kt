package com.example.melascan.feature_Prediction.domain.repository

import com.example.melascan.feature_melascan.domain.model.Prediction
import kotlinx.coroutines.flow.Flow

interface PredictionRepository {

    suspend fun nukeTheRepository()

    fun getPredictions(): Flow<List<Prediction>>

    suspend fun getPredictionById(id: Int): Prediction?

    suspend fun insertPrediction(prediction: Prediction)

    suspend fun deletePrediction(prediction: Prediction)

    suspend fun getSize(): Int

    suspend fun getLatestPrediction(): Prediction
}