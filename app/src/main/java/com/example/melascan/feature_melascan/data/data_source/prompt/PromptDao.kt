package com.example.melascan.feature_melascan.data.data_source.prompt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.melascan.feature_melascan.domain.model.Prompts
import kotlinx.coroutines.flow.Flow

@Dao
interface PromptDao {
    @Query("SELECT * FROM prompts")
    fun getPrompts(): Flow<List<Prompts>>

    @Query("SELECT * FROM prompts WHERE id = :id")
    suspend fun getPromptById(id: Int): Prompts?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrompt(prompt: Prompts)

    @Query("UPDATE Prompts SET promptedPhoto = :promptedPhoto WHERE id = :id")
    suspend fun setPrompt(promptedPhoto: Boolean, id: Int)

    @Delete
    suspend fun deletePrompt(prompt: Prompts)
}