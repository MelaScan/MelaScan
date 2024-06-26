package com.example.melascan.feature_melascan.presentation.images_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.feature_melascan.domain.model.Prompts
import com.example.melascan.feature_melascan.domain.use_case.prediction.PredictionUseCases
import com.example.melascan.feature_melascan.domain.use_case.prompts.PromptsUseCases
import com.example.melascan.feature_melascan.domain.util.OrderType
import com.example.melascan.feature_melascan.domain.util.PredictionsOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject


@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val predictionUseCases: PredictionUseCases,
    private val promptUseCases: PromptsUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = mutableStateOf(ImagesState())
    val state: State<ImagesState> = _state

    private var getImagesJob: Job? = null
    private var getPromptsJob: Job? = null

    init {
        /*viewModelScope.launch {
            predictionUseCases.nukePredictions() // :)
        }*/
        getImages(PredictionsOrder.Date(OrderType.Descending))
        getPrompts()
    }


    fun getBitmap(context: Context, prediction: Prediction): Bitmap? {
        return state.value.bitmaps[prediction.id]
            ?: run {
                viewModelScope.launch {
                    val bitmap = loadBitmap(context, prediction)
                    _state.value = state.value.copy(
                        bitmaps = state.value.bitmaps + (prediction.id!! to bitmap)
                    )
                }
                null
            }
    }

    private suspend fun loadBitmap(context: Context, prediction: Prediction): Bitmap {
        val uriImage = Uri.parse(prediction.photoPath)
        return withContext(Dispatchers.IO) {
            ImageDecoder.createSource(context.contentResolver, uriImage).let {
                ImageDecoder.decodeBitmap(it)
            }
        }
    }

    fun onEvent(event: ImagesEvent) {
        when(event) {
            is ImagesEvent.Order -> {
                if(state.value.predictionsOrder::class == event.predictionsOrder::class
                    && state.value.predictionsOrder.orderType == event.predictionsOrder.orderType) {
                    return // exit early if stuff is the same
                }
                getImages(event.predictionsOrder)
            }
            ImagesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            ImagesEvent.SetPromptTrue -> {
                setPrompts(Prompts(promptedPhoto = true))
                getPrompts()
            }
        }
    }


    private fun getImages(predictionsOrder: PredictionsOrder) {
        getImagesJob?.cancel()
        getImagesJob = predictionUseCases.getPredictions(predictionsOrder)
            .onEach { predictions ->

                val newList = predictions.toMutableList()

                // remove duplicates
                val set = mutableSetOf<String>()
                newList.removeIf { prediction ->
                    !set.add(prediction.photoPath)
                }

                _state.value = state.value.copy(
                    predictions = newList,
                    predictionsOrder = predictionsOrder
                )
            }
            .launchIn(viewModelScope)
    }

    private fun getPrompts() {
        getPromptsJob?.cancel()
        getPromptsJob = promptUseCases.getPrompts()
            .onEach { prompts ->

                // if database is empty then fill it with a default prompt
                if(prompts.isEmpty()) {
                    val prompt = Prompts(promptedPhoto = false)
                    promptUseCases.addPrompt(prompt)
                    _state.value = state.value.copy(
                        prompts = prompt
                    )
                    return@onEach
                }


                _state.value = state.value.copy(
                    prompts = prompts[0]
                )
            }
            .launchIn(viewModelScope)
    }

    private fun setPrompts(prompts: Prompts) {
        // idk how to assign a job to this. oh well.
        viewModelScope.launch {
            promptUseCases.setPrompts(prompts)
        }
    }

}