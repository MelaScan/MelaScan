package com.example.melascan.feature_melascan.domain.AI.Models

import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.util.Arrays
import java.util.function.Function
import kotlin.math.exp
import kotlin.math.round


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

// @TODO(AI GENERATED CHECK THE MATH LATER)
fun softmax(neuronValues: FloatArray): Float {
    val input = neuronValues[AIModel.MelanomaIndex.index]
    val total: Double = neuronValues.sumOf { exp(it.toDouble()) }
    return (round(exp(input.toDouble()) / total*10_000)/10_000).toFloat() // constrict to 4 decimal places
}