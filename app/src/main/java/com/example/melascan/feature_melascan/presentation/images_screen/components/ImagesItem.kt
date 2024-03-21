package com.example.melascan.feature_melascan.presentation.images_screen.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.melascan.feature_melascan.domain.model.Prediction

@Composable
fun ImagesItem(
    prediction: Prediction,
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
        )
        val list = floatArrayOf(prediction.akiec, prediction.bcc, prediction.bkl, prediction.df, prediction.mel, prediction.nv, prediction.vasc)

        var max: Float = Float.MIN_VALUE
        var index = 0

        list.forEachIndexed { i, item ->
            if (item > max) {
                max = item
                index = i
            }
        }

        val label = when (index) {
            0 -> "Actinic Keratoses and Intraepithelial Carcinoma or Bowen's disease"
            1 -> "Basal Cell Carcinoma "
            2 -> "Benign Keratosis-Like Lesion"
            3 -> "Dermatofibroma "
            4 -> "Melanoma"
            5 -> "Melanocytic Nevi"
            6 -> "Vascular Lesions"
            else -> "unknown"
        }

        Text("Strongest prediction: \'$label\' at ${max*100}%", color = Color.White)
    }
}