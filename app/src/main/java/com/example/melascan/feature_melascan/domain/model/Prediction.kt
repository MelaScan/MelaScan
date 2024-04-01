package com.example.melascan.feature_melascan.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Prediction(
    // we're only storing essential information for the prediction.
    val ageApprox: Int,
    val anomSiteGeneral: Int,
    val benignOrMalignant: Float, // 0 for benign and 1 for malignant.
    val timestamp: Long, // time of when the photo was taken (helps for metrics and will identify photo if moved around on device (low chances of same photo having perfectly matching timestamps))
    val photoPath: String, // path to the photo
    @PrimaryKey val id: Int? = null
) {
    companion object {
        @Throws(InvalidPredictionException::class)
        fun validatePrediction(prediction: Prediction) {

            if (prediction.photoPath.isBlank()) throw InvalidPredictionException("photoPath must not be blank")

            Log.i("AddPrediction", "Prediction is valid!")

        }
    }
}

enum class BodyLocation {
    Head,
    Torso,
    UpperExtremity,
    LowerExtremity,
    PalmsSoles,
}

class InvalidPredictionException(message: String) : Exception(message)
class InvalidPredictionImageException(message: String) : Exception(message)