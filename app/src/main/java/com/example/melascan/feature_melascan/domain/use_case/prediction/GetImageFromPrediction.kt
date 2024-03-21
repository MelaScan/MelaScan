package com.example.melascan.feature_melascan.domain.use_case.prediction

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository
import com.example.melascan.feature_melascan.domain.model.InvalidPredictionImageException

// Note: It actually returns an Android.graphics.Bitmap, not an Image :)
class GetImageFromPrediction(
    private val repository: PredictionRepository
) {

    @Throws(InvalidPredictionImageException::class)
    suspend operator fun invoke(id: Int): Bitmap {

        val bp = BitmapFactory.decodeFile(repository.getPredictionById(id)?.photoPath)

        return when(bp) {
            null -> throw InvalidPredictionImageException("Image not found in path: ${repository.getPredictionById(id)?.photoPath}")
            else -> bp
        }
    }
}