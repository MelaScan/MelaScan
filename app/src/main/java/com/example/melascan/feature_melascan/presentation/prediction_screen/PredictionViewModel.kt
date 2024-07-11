package com.example.melascan.feature_melascan.presentation.prediction_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.melascan.feature_melascan.domain.AI.ModelSizes
import com.example.melascan.feature_melascan.domain.AI.models.AIModel
import com.example.melascan.feature_melascan.domain.AI.models.BalancedModel
import com.example.melascan.feature_melascan.domain.AI.models.LiteModel
import com.example.melascan.feature_melascan.domain.AI.models.SuperModel
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.feature_melascan.domain.use_case.prediction.PredictionUseCases
import com.example.melascan.feature_melascan.presentation.data_screen.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel()
class PredictionViewModel @Inject constructor(
    private val predictionUseCases: PredictionUseCases,
): ViewModel() {
    private var setPredictionJob: Job? = null

    var prediction: Prediction? = null

    private val _state = mutableStateOf(false)

    val state: State<Boolean> = _state

    @OptIn(DelicateCoroutinesApi::class)
    fun makePrediction(
        dataState: DataState,
        fileName: String,
        context: Context,
    ) {
        setPredictionJob?.cancel()
        val aiModel: AIModel = when(dataState.modelSizes) {
            ModelSizes.Lite -> LiteModel(
                dataState.age!!,
                dataState.bodyLocation,
                fileName,
                dataState.isHidden,
                context
            )
            ModelSizes.Balanced -> BalancedModel(
                dataState.age!!,
                dataState.bodyLocation,
                fileName,
                dataState.isHidden,
                context
            )
            ModelSizes.Super -> SuperModel(
                dataState.age!!,
                dataState.bodyLocation,
                fileName,
                dataState.isHidden,
                context
            )
        }
        setPredictionJob = GlobalScope.launch {
            prediction = aiModel.run()
            predictionUseCases.addPrediction(prediction!!)
        }
        setPredictionJob?.invokeOnCompletion {
            _state.value = true
        }
    }
}