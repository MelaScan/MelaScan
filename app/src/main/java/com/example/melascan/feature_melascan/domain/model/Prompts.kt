package com.example.melascan.feature_melascan.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// THERE SHOULD ONLY BE ONE 'PROMPTS' IN THE DB!!!!
@Entity
data class Prompts(
    // phrase all prompt vars as questions that are answered with t/f
    val promptedPhoto: Boolean = false,
    @PrimaryKey val id: Int? = null
)

class InvalidPromptsException(message: String) : Exception(message)