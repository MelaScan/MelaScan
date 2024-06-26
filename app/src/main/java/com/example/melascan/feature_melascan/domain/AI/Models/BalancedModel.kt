package com.example.melascan.feature_melascan.domain.AI.Models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import androidx.collection.floatListOf
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.util.fastJoinToString
import androidx.compose.ui.util.trace
import com.example.melascan.feature_melascan.domain.model.BodyLocation
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.ml.BalancedModelTf
import kotlinx.coroutines.delay
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.model.Model.Options.Builder
import java.util.concurrent.TimeUnit

class BalancedModel(
    private val age: Int,
    private val bodyLocation: BodyLocation,
    private val uriString: String,
    private val isHidden: Boolean = false,
    context: Context,
    private val balancedModel: BalancedModelTf = BalancedModelTf.newInstance(
        context,
        Builder().setDevice(
            Model.Device.NNAPI
        ).build() // this might stall the thread :/
    )
): AIModel(
    ImageDecoder.createSource(context.contentResolver, Uri.parse(uriString)).let {
        ImageDecoder.decodeBitmap(it)
    }
) {
    override suspend fun run(): Prediction {

        val imageProcessor  = ImageProcessor.Builder()
            .add(ResizeOp(512, 512, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(floatArrayOf(0.485f, 0.456f, 0.406f), floatArrayOf(0.229f, 0.224f, 0.225f)))
            .build()

        val floatList = listOf(
            1.0f, /*sex: this shouldn't effect prediction might add later*/
            age.toFloat(), /*age*/
            1.0f, /*n_images*/
            512.0f, /*image size*/
            if (bodyLocation == BodyLocation.AnteriorTorso) 1.0f else 0.0f, // anterior torso
            if (bodyLocation == BodyLocation.HeadOrNeck) 1.0f else 0.0f, // head or neck
            if (bodyLocation == BodyLocation.LateralTorso) 1.0f else 0.0f, // lateral torso
            if (bodyLocation == BodyLocation.LowerExtremity) 1.0f else 0.0f, // lower extremity
            if (bodyLocation == BodyLocation.OralGenital) 1.0f else 0.0f, // oral genital
            if (bodyLocation == BodyLocation.PalmsSoles) 1.0f else 0.0f, // palms soles
            if (bodyLocation == BodyLocation.PosteriorTorso) 1.0f else 0.0f, // posterior torso
            if (bodyLocation == BodyLocation.Torso) 1.0f else 0.0f, // torso
            if (bodyLocation == BodyLocation.UpperExtremity) 1.0f else 0.0f, // upper extremity
            0.0f, // unknown location
        )

        val metaData = createTensorBuffer(floatList)

        val outputs = balancedModel.process(
            imageProcessor.process(TensorImage.fromBitmap(bitmap.copy(Bitmap.Config.ARGB_8888, true))).tensorBuffer,
            metaData
        ) // this is where the magic happens


        val listOFloats: List<Float> = emptyList()
        outputs.outputFeature0AsTensorBuffer.floatArray.forEachIndexed { index, fl ->
            println("Item $index is $fl")
            listOFloats + fl;
        }
        println("List of floats $listOFloats")


        val percentMelanoma = softmax(outputs.outputFeature0AsTensorBuffer.floatArray)

        if (percentMelanoma.isNaN()) {
            throw Exception("Percent Melanoma is NaN!!!")
        }

        print("Percent melanoma $percentMelanoma")

        balancedModel.close()

        return Prediction(
            ageApprox = age,
            anomSiteGeneral = bodyLocation.ordinal,
            benignOrMalignant = percentMelanoma,
            timestamp = System.currentTimeMillis(),
            photoPath = uriString,
            height = (150..300).random(),
            isHidden = isHidden
        )
    }
}