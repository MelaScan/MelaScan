package com.example.melascan.feature_melascan.presentation.home_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melascan.feature_melascan.domain.model.Prompts
import com.example.melascan.feature_melascan.domain.use_case.prompts.PromptsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val promptsUseCases: PromptsUseCases,
): ViewModel() {
    private var getPromptsJob: Job? = null

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getPrompts()
    }

    private fun getPrompts() {
        getPromptsJob?.cancel()
        getPromptsJob = promptsUseCases.getPrompts()
            .onEach { prompts ->
                if (prompts.isEmpty()) {
                    val prompt = Prompts(promptedTopBar = false, promptedPhoto = false)
                    promptsUseCases.addPrompt(prompt)
                    _state.value = HomeState(prompt)
                    return@onEach
                }

                _state.value = HomeState(prompts[0])
            }.launchIn(viewModelScope)
    }

    fun setPromptsTrue() {
        viewModelScope.launch {
            // we'll just run it in coroutines cause why not?
            _state.value = HomeState(
                _state.value.prompts?.copy(promptedTopBar = true)
                    ?: Prompts(promptedTopBar = true)
            )
            promptsUseCases.setPromptsTopBar(
                _state.value.prompts?.copy(promptedTopBar = true)
                    ?: Prompts(promptedTopBar = true)
            )
        }
    }

}