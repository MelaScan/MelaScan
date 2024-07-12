package com.example.melascan.feature_melascan.domain.AI.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import com.example.melascan.feature_melascan.domain.model.BodyLocation
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.ml.LiteModelTf
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.model.Model.Options.Builder


class LiteModel(
    private val age: Int,
    private val bodyLocation: BodyLocation,
    private val uriString: String,
    private val isHidden: Boolean = false,
    context: Context,
    private val liteModel: LiteModelTf = LiteModelTf.newInstance(
        context,
        Builder().setDevice(
            Model.Device.NNAPI
        ).build()
    )
): AIModel(
    ImageDecoder.createSource(context.contentResolver, Uri.parse(uriString)).let {
        ImageDecoder.decodeBitmap(it)
    }
) {
    override suspend fun run(): Prediction {
        val imageProcessor  = getImgProcessor(448)

        val outputs = liteModel.process(
            imageProcessor.process(TensorImage.fromBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, true))).tensorBuffer,
        ) // this is where the magic happens

        val floatArray = softmax(outputs.outputFeature0AsTensorBuffer.floatArray)

        val listOFloats: List<Float> = emptyList()
        floatArray.forEachIndexed { index, fl ->
            println("Item $index is $fl")
            listOFloats + fl;
        }
        println("List of floats $listOFloats")

        val percentMelanoma = floatArray[MelanomaIndex.MEL]

        if (percentMelanoma.isNaN()) {
            throw Exception("Percent Melanoma is NaN!!!")
        }

        Log.i("BalancedModel","Percent melanoma $percentMelanoma")

        liteModel.close()

        return Prediction(
            ageApprox = age,
            anomSiteGeneral = bodyLocation.ordinal,
            percentMelanoma = percentMelanoma,
            percentAK  = floatArray[MelanomaIndex.AK],
            percentBCC = floatArray[MelanomaIndex.BCC],
            percentBKL = floatArray[MelanomaIndex.BKL],
            percentDF = floatArray[MelanomaIndex.DF],
            percentSCC = floatArray[MelanomaIndex.SCC],
            percentVASC = floatArray[MelanomaIndex.VASC],
            percentNEVUS = floatArray[MelanomaIndex.NEVUS],
            percentOTHER = floatArray[MelanomaIndex.OTHER],
            timestamp = System.currentTimeMillis(),
            photoPath = uriString,
            height = (150..300).random(),
            isHidden = isHidden
        )
    }
}