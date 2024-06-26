package com.example.melascan.feature_melascan.presentation.image_screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.feature_melascan.domain.use_case.prediction.PredictionUseCases
import com.example.melascan.feature_melascan.presentation.images_screen.ImagesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val predictionUseCases: PredictionUseCases
): ViewModel() {

    private val _state = mutableStateOf(null as Prediction?)
    val state: State<Prediction?> = _state

    fun setup(id: Int) {
        viewModelScope.launch {
            _state.value = predictionUseCases.getPrediction(id)
        }
    }

    fun getBitmap(context: Context, prediction: Prediction): Bitmap {
        val uriImage = Uri.parse(prediction.photoPath)
        return ImageDecoder.createSource(context.contentResolver, uriImage).let {
            ImageDecoder.decodeBitmap(it)
        }
    }


}