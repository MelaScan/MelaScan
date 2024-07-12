package com.example.melascan.feature_melascan.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Prediction(
    // we're only storing essential information for the prediction.
    val ageApprox: Int,
    val anomSiteGeneral: Int,
    val percentMelanoma: Float, // 0 for benign and 1 for malignant.
    val percentAK: Float, // Actinic Keratosis
    val percentBCC: Float, // Basal Cell Carcinomaa
    val percentBKL: Float, // Benign Keratosis
    val percentDF: Float, // Dermatofibroma
    val percentSCC: Float, // Squamous cell carcinoma
    val percentVASC: Float, // Vascular Lesion
    val percentNEVUS: Float, // Melanocytic nevus
    val percentOTHER: Float,// unknown
    val timestamp: Long, // time of when the photo was taken (helps for metrics and will identify photo if moved around on device (low chances of same photo having perfectly matching timestamps))
    val photoPath: String, // path to the photo
    val height: Int = 100, // height for the displayed image
    val isHidden: Boolean = false, // is the image hidden
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
    HeadOrNeck,
    AnteriorTorso,
    LateralTorso,
    UpperExtremity,
    LowerExtremity,
    PalmsSoles,
    OralGenital,
    Torso,
    PosteriorTorso
}

class InvalidPredictionException(message: String) : Exception(message)
class InvalidPredictionImageException(message: String) : Exception(message)