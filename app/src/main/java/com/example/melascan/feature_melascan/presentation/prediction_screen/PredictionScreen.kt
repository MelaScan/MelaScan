package com.example.melascan.feature_melascan.presentation.prediction_screen

import android.widget.Toast
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.feature_melascan.presentation.data_screen.DataState
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.feature_melascan.presentation.util.components.CircleBackground
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@Composable
fun PredictionScreen(
    navController: NavController,
    fileName: String?,
    dataState: DataState,
    viewModel: PredictionViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    viewModel.makePrediction(dataState, fileName!!.replace('^','/'), context)

    LaunchedEffect(viewModel.state) {
        navController.navigate(Screen.ImageScreen.route)
    }

    Scaffold {
        CircleBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Computing!\nDon't turn your screen off or close the app!",
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
        }
    }


}