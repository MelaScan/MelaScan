package com.example.melascan.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.melascan.feature_note.domain.model.Prediction

@Database(
    entities = [Prediction::class],
    version = 1
)
abstract class PredictionDatabase : RoomDatabase() {

    abstract val predictionDao: PredictionDao

    companion object {
        const val DATABASE_NAME = "PredictionDB"
    }
}