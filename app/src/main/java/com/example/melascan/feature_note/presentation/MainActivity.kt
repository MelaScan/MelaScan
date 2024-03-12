package com.example.melascan.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import com.example.melascan.ui.theme.MelaScanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MelaScanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val onBg = MaterialTheme.colorScheme.onBackground
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        translate(left = 750f, top = -300f) {
                            drawCircle(onBg, radius = 200.dp.toPx())
                        }
                    }

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        translate(left = -800f, top = 50f) {
                            drawCircle(onBg, radius = 150.dp.toPx())
                        }
                    }

                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                            Greeting("Android", MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = color,
    )
}