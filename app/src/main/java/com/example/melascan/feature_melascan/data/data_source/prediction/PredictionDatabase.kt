package com.example.melascan.feature_melascan.data.data_source.prediction

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.melascan.feature_melascan.domain.model.Prediction

@Database(
    entities = [Prediction::class],
    version = 4
)
abstract class PredictionDatabase : RoomDatabase() {

    abstract val predictionDao: PredictionDao

    companion object {
        const val DATABASE_NAME = "PredictionsDB"
    }
}