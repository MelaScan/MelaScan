package com.example.melascan.feature_melascan.domain.use_case.prompts

import com.example.melascan.feature_melascan.domain.model.Prompts
import com.example.melascan.feature_melascan.domain.repository.PromptsRepository

class SetPromptsPhoto(
    private val repository: PromptsRepository
) {
    suspend operator fun invoke(prompts: Prompts) {
        repository.setPromptsPhoto(prompts)
    }
}