package com.example.melascan.feature_melascan.presentation.data_screen

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel

@HiltViewModel
class DataViewModel @Inject constructor(
    // shouldn't need anything in the constructor
): ViewModel() {
    private val _state = mutableStateOf(DataState())
    val state: State<DataState> = _state

    fun onEvent(dataEvent: DataEvent) {
        when(dataEvent) {
            is DataEvent.NavigateToAI -> {
                // bring data state to global state
                dataEvent.func.invoke(state.value)
            }
            is DataEvent.UpdatedAge -> {
                _state.value = _state.value.copy(
                    age = dataEvent.age
                )
            }
            is DataEvent.UpdateLocationDropdown -> {
                _state.value = _state.value.copy(
                    bodyLocation = dataEvent.bodyLocation
                )
            }
            is DataEvent.UpdatedModelSize -> {
                _state.value = _state.value.copy(modelSizes = dataEvent.modelSizes)
            }

            is DataEvent.UpdatePrivateCheckbox -> {
                _state.value = _state.value.copy(isHidden = dataEvent.value)
            }
        }
    }

}