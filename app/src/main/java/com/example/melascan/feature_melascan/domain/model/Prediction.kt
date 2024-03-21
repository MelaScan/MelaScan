package com.example.melascan.feature_melascan.domain.model

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Prediction(
    // the following are confidence metrics from 0.0-1.0
    val akiec: Float, // Actinic keratoses and intraepithelial carcinoma / Bowen's disease
    val bcc: Float, // basal cell carcinoma
    val bkl: Float, // benign keratosis-like lesions (solar lentigines / seborrheic keratoses and lichen-planus like keratoses)
    val df: Float, // dermatofibroma
    val mel: Float, // melanoma
    val nv: Float, // melanocytic nevi
    val vasc: Float, // vascular lesions (angiomas, angiokeratomas, pyogenic granulomas and hemorrhage)
    val timestamp: Long, // time of when the photo was taken (helps for metrics and will identify photo if moved around on device (low chances of same photo having perfectly matching timestamps))
    val photoPath: String, // path to the photo
    @PrimaryKey val id: Int? = null
) {
    companion object {
        @Throws(InvalidPredictionException::class)
        fun validatePrediction(prediction: Prediction) {

            // check for a bunch of issues then throw an error if any are found :)
            when {
                prediction.akiec < 0.0 || prediction.akiec > 1.0 -> throw InvalidPredictionException(
                    "akiec confidence metric must be between 0.0 and 1.0"
                )

                prediction.bcc < 0.0 || prediction.bcc > 1.0 -> throw InvalidPredictionException("bcc confidence metric must be between 0.0 and 1.0")
                prediction.bkl < 0.0 || prediction.bkl > 1.0 -> throw InvalidPredictionException("bkl confidence metric must be between 0.0 and 1.0")
                prediction.df < 0.0 || prediction.df > 1.0 -> throw InvalidPredictionException("df confidence metric must be between 0.0 and 1.0")
                prediction.mel < 0.0 || prediction.mel > 1.0 -> throw InvalidPredictionException("mel confidence metric must be between 0.0 and 1.0")
                prediction.nv < 0.0 || prediction.nv > 1.0 -> throw InvalidPredictionException("nv confidence metric must be between 0.0 and 1.0")
                prediction.vasc < 0.0 || prediction.vasc > 1.0 -> throw InvalidPredictionException("vasc confidence metric must be between 0.0 and 1.0")
            }
            if (prediction.photoPath.isBlank()) throw InvalidPredictionException("photoPath must not be blank")

            if (prediction.akiec == 0.0f && prediction.bcc == 0.0f && prediction.bkl == 0.0f && prediction.df == 0.0f && prediction.mel == 0.0f && prediction.nv == 0.0f && prediction.vasc == 0.0f) throw InvalidPredictionException(
                "at least one confidence metric must be greater than 0.0"
            )

            Log.i("AddPrediction", "Prediction is valid!")

        }
    }
}

class InvalidPredictionException(message: String) : Exception(message)
class InvalidPredictionImageException(message: String) : Exception(message)