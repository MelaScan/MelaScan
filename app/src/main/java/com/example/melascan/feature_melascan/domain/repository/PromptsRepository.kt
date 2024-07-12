package com.example.melascan.feature_melascan.domain.repository

import com.example.melascan.feature_melascan.domain.model.Prompts
import kotlinx.coroutines.flow.Flow

interface PromptsRepository {

    /* TODO: Remove unnecessary functions in the future */
    fun getPrompts(): Flow<List<Prompts>>

    suspend fun getPromptsById(id: Int): Prompts?

    suspend fun insertPrompts(prompts: Prompts)

    suspend fun setPromptsPhoto(prompts: Prompts)

    suspend fun setPromptsTopBar(prompts: Prompts)

    suspend fun deletePrompts(prompts: Prompts)

}