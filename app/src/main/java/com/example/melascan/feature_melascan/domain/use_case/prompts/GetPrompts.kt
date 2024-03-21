package com.example.melascan.feature_melascan.domain.use_case.prompts

import com.example.melascan.feature_melascan.domain.model.InvalidPromptsException
import com.example.melascan.feature_melascan.domain.model.Prompts
import com.example.melascan.feature_melascan.domain.repository.PromptsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList

class GetPrompts(
    private val repository: PromptsRepository
) {
    operator fun invoke(): Flow<List<Prompts>> {
        // this should work
        return repository.getPrompts()
    }
}