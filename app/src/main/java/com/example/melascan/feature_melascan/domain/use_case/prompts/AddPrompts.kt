package com.example.melascan.feature_melascan.domain.use_case.prompts

import com.example.melascan.feature_melascan.domain.model.Prompts
import com.example.melascan.feature_melascan.domain.repository.PromptsRepository

class AddPrompts(
    private val repository: PromptsRepository
) {
    // It isn't really throwing an error rn, but it could be modified to do so
    //@Throws(InvalidPromptsException::class)
    suspend operator fun invoke(prompts: Prompts) {
        repository.insertPrompts(prompts)
    }
}