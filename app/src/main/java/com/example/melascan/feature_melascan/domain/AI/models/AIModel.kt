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
        const val MEL = 6 // directly from the cmd tool:

        const val AK = 0 // Actinic Keratosis
        const val BCC = 1 // Basal Cell Carcinoma
        const val BKL = 2 // Benign Keratosis
        const val DF = 3 // Dermatofibroma
        const val SCC = 4 // Squamous cell carcinoma
        const val VASC = 5 // Vascular Lesion
        const val NEVUS = 7 // Melanocytic nevus
        const val OTHER = 8 // unknown

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
            percentMelanoma = (1..100).random()/100f,
            percentAK  = (1..100).random()/100f,
            percentBCC = (1..100).random()/100f,
            percentBKL = (1..100).random()/100f,
            percentDF = (1..100).random()/100f,
            percentSCC = (1..100).random()/100f,
            percentVASC = (1..100).random()/100f,
            percentNEVUS = (1..100).random()/100f,
            percentOTHER = (1..100).random()/100f,
            timestamp = System.currentTimeMillis(),
            photoPath = uriString,
            height = (150..300).random(),
            isHidden = isHidden
        )
    }

}