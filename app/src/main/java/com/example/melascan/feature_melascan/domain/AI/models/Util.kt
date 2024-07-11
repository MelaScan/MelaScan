package com.example.melascan.feature_melascan.domain.AI.models

import com.example.melascan.feature_melascan.domain.model.BodyLocation
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import kotlin.math.exp


fun createTensorBuffer(floatList: List<Float>): TensorBuffer {
    // Ensure the list has exactly 14 items
    require(floatList.size == 14) { "Input list must contain exactly 14 items" }

    // Create a TensorBuffer with shape [14] and data type FLOAT32
    val tensorBuffer = TensorBuffer.createFixedSize(intArrayOf(14), DataType.FLOAT32)

    // Convert the List<Float> to FloatArray
    val floatArray = floatList.toFloatArray()

    // Load the data into the TensorBuffer
    tensorBuffer.loadArray(floatArray)

    return tensorBuffer
}

fun softmax(input: FloatArray): FloatArray {
    val max = input.maxOrNull() ?: 0f
    val exp = input.map { exp((it - max).toDouble()).toFloat() }
    val sum = exp.sum()
    return exp.map { it / sum }.toFloatArray()
}

fun getImgProcessor(imgSize: Int): ImageProcessor = ImageProcessor.Builder()
    .add(ResizeOp(imgSize, imgSize, ResizeOp.ResizeMethod.BILINEAR))
    .add(NormalizeOp(floatArrayOf(0.485f, 0.456f, 0.406f), floatArrayOf(0.229f, 0.224f, 0.225f)))
    .build()

/*
        # - hard-code meta features as needed
        _used_meta_features = [
            'sex',
            'age_approx',
            'n_images',
            'image_size',
            'site_anterior torso',
            'site_head/neck',
            'site_lateral torso',
            'site_lower extremity',
            'site_oral/genital',
            'site_palms/soles',
            'site_posterior torso',
            'site_torso',
            'site_upper extremity',
            'site_nan',
]*/
fun getMetadata(imgSize: Int, age: Int, bodyLocation: BodyLocation): TensorBuffer {
    val floatList = listOf(
        1.0f, /*sex: this shouldn't effect prediction might add later*/
        age.toFloat()/90f, /*age idk why we divide by 90 but it's important*/
        1.0f, /*n_images*/
        imgSize.toFloat(), /*image size*/
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
    return createTensorBuffer(floatList)
}