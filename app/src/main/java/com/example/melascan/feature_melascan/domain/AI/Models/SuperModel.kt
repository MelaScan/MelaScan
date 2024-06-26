package com.example.melascan.feature_melascan.domain.AI.Models

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import com.example.melascan.feature_melascan.domain.model.BodyLocation
import com.example.melascan.feature_melascan.domain.model.Prediction
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.random.Random

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
        delay(TimeUnit.SECONDS.toMillis(4))


        return Prediction(
            ageApprox = age,
            anomSiteGeneral = bodyLocation.ordinal,
            benignOrMalignant = (0..12).random()/100.0f,
            timestamp = System.currentTimeMillis(),
            photoPath = uriString,
            height = (150..300).random(),
            isHidden = isHidden
        )
    }
}