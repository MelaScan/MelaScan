package com.example.melascan.feature_melascan.domain.AI.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import com.example.melascan.feature_melascan.domain.model.BodyLocation
import com.example.melascan.feature_melascan.domain.model.Prediction

abstract class AIModel(protected val bitmap: Bitmap) {
    abstract suspend fun run(): Prediction

    object MelanomaIndex {
        val index = 6 // directly from the cmd tool:
        /*
            # hard-coded melanoma diagnosis output index
            _diags_full = ['ak', 'bcc', 'bkl', 'df', 'scc', 'vasc', 'melanoma', 'nevus', 'unknown']
        */
    }

}

class SuperModel(
    private val age: Int,
    private val bodyLocation: BodyLocation,
    private val uriString: String,
    private val isHidden: Boolean = false,
    context: Context,
): AIModel(
    ImageDecoder.createSource(context.contentResolver, Uri.parse(uriString)).let {
        ImageDecoder.decodeBitmap(it)
    }
) {
    override suspend fun run(): Prediction {
        return Prediction(
            ageApprox = age,
            anomSiteGeneral = bodyLocation.ordinal,
            benignOrMalignant = (1..100).random()/100f,
            timestamp = System.currentTimeMillis(),
            photoPath = uriString,
            height = (150..300).random(),
            isHidden = isHidden
        )
    }

}