package com.example.melascan.feature_note.domain.model

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
)

class InvalidPredictionException(message: String) : Exception(message)