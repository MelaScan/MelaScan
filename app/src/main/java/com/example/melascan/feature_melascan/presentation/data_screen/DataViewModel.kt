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
                _state.value = DataState(
                    age = dataEvent.age,
                    bodyLocation = _state.value.bodyLocation,
                    modelSizes = _state.value.modelSizes
                )
            }
            is DataEvent.UpdatedLocationDropdown -> {
                _state.value = DataState(
                    age = _state.value.age,
                    bodyLocation = dataEvent.bodyLocation,
                    modelSizes = _state.value.modelSizes
                )
            }
            is DataEvent.UpdatedModelSize -> {
                _state.value = DataState(
                    age = _state.value.age,
                    bodyLocation = _state.value.bodyLocation,
                    modelSizes = dataEvent.modelSizes
                )
            }
        }
    }

}