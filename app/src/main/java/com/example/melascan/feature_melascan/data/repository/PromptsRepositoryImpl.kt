package com.example.melascan.feature_melascan.data.repository

import com.example.melascan.feature_melascan.data.data_source.prompt.PromptDao
import com.example.melascan.feature_melascan.domain.model.Prompts
import com.example.melascan.feature_melascan.domain.repository.PromptsRepository
import kotlinx.coroutines.flow.Flow

class PromptsRepositoryImpl(
    private val dao: PromptDao
): PromptsRepository {
    override fun getPrompts(): Flow<List<Prompts>> {
        return dao.getPrompts()
    }

    override suspend fun getPromptsById(id: Int): Prompts? {
        return dao.getPromptById(id)
    }

    override suspend fun insertPrompts(prompts: Prompts) {
        return dao.insertPrompt(prompts)
    }

    override suspend fun deletePrompts(prompts: Prompts) {
        return dao.deletePrompt(prompts)
    }

    override suspend fun setPromptsPhoto(prompts: Prompts) {
        return dao.setPromptPhoto(prompts.promptedPhoto, 1) // should be a constant
    }

    override suspend fun setPromptsTopBar(prompts: Prompts) {
        return dao.setPromptBar(prompts.promptedTopBar, 1)
    }

}