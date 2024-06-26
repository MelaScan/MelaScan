package com.example.melascan.feature_melascan.presentation.util.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
@Composable
fun CircleBackground() {
    val onBg = Color(0xffd8dcd2)
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(left = 750f, top = -300f) {
            drawCircle(onBg, radius = 200.dp.toPx())
        }

        translate(left = -800f, top = 50f) {
            drawCircle(onBg, radius = 150.dp.toPx())
        }

        translate(left = 500f, top = 975f) {
            drawCircle(onBg, radius = 125.dp.toPx())
        }

    }
}