package com.example.melascan.feature_melascan.domain.AI.Models

import android.graphics.Bitmap
import com.example.melascan.feature_melascan.domain.model.Prediction
import org.checkerframework.common.reflection.qual.Invoke

abstract class AIModel(protected val bitmap: Bitmap) {
    abstract suspend fun run(): Prediction

    object MelanomaIndex {
        val index = 6 // directly from the cmd tool:
    }
    /*
    # hard-coded melanoma diagnosis output index
    _diags_full = ['ak', 'bcc', 'bkl', 'df', 'scc', 'vasc', 'melanoma', 'nevus', 'unknown']
    */
}