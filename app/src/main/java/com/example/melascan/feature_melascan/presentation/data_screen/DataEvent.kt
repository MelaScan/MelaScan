package com.example.melascan.feature_melascan.presentation.data_screen

import com.example.melascan.feature_melascan.domain.AI.ModelSizes
import com.example.melascan.feature_melascan.domain.model.BodyLocation

sealed class DataEvent {
    data class UpdatedModelSize(val modelSizes: ModelSizes): DataEvent()
    data class UpdatedAge(val age: Int?): DataEvent()
    data class UpdateLocationDropdown(val bodyLocation: BodyLocation): DataEvent()
    data class UpdatePrivateCheckbox(val value: Boolean): DataEvent()
    data class NavigateToAI(val func: (DataState) -> Unit): DataEvent()

}