package com.example.melascan.feature_melascan.data.data_source.prediction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.melascan.feature_melascan.domain.model.Prediction
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictionDao {
    @Query("SELECT * FROM prediction")
    fun getPredictions(): Flow<List<Prediction>>

    @Query("SELECT * FROM prediction WHERE id = :id")
    suspend fun getPredictionById(id: Int): Prediction?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrediction(prediction: Prediction)

    @Delete
    suspend fun deletePrediction(prediction: Prediction)
}