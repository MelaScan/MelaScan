package com.example.melascan.feature_note.data.data_source.prompt

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.melascan.feature_note.domain.model.Prompts

@Database(
    entities = [Prompts::class],
    version = 1
)
abstract class PromptDatabase: RoomDatabase() {

    abstract val promptDao: PromptDao

    companion object {
        const val DATABASE_NAME = "PromptDB"
    }
}