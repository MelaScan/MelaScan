package com.example.melascan.feature_melascan.domain.AI.Models

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import com.example.melascan.feature_melascan.domain.model.BodyLocation
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.ml.LiteModelTf
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


class LiteModel(
    private val age: Int,
    private val bodyLocation: BodyLocation,
    private val uriString: String,
    private val isHidden: Boolean = false,
    context: Context,
    private val liteModel: LiteModelTf = LiteModelTf.newInstance(context)
): AIModel(
    ImageDecoder.createSource(context.contentResolver, Uri.parse(uriString)).let {
        ImageDecoder.decodeBitmap(it)
    }
) {
    override suspend fun run(): Prediction {
        delay(TimeUnit.SECONDS.toMillis(1))



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