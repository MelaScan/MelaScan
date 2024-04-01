package com.example.melascan.feature_melascan.presentation.images_screen

import android.graphics.Bitmap
import android.graphics.Paint.Align
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.images_screen.components.ImagesItem
import com.example.melascan.feature_melascan.presentation.images_screen.components.OrderSection
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold
import kotlinx.coroutines.launch

@Composable
fun ImagesScreen(
    navController: NavController,
    viewModel: ImagesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    MelaScaffold(navController = navController, scope = scope, floatingActionButton = {
        ExtendedFloatingActionButton(
            onClick = {
                if(!state.prompts.promptedPhoto) {
                    Toast.makeText(context, "Redirect to Photo Prompt!", Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(ImagesEvent.SetPromptTrue)
                    navController.navigate(Screen.ImagePromptScreens.One.route)
                } else {
                    Toast.makeText(context, "Redirect to Photo!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.TakePhoto.route)
                }
            },
            icon = { Icon(Icons.Filled.CameraAlt, "Camera action button") },
            text = { Text(text = "Take a Photo") },
            containerColor = MaterialTheme.colorScheme.secondary
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0x40829B66), shape = RoundedCornerShape(15.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "Your Images",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = {
                    viewModel.onEvent(ImagesEvent.ToggleOrderSection)
                },) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Sort,
                        contentDescription = "Sort",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    imagesOrder = state.predictionsOrder,
                ) { predOrder ->
                        viewModel.onEvent(ImagesEvent.Order(predOrder))
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            if (state.predictions.isEmpty()) {
                Text(
                    "Currently you haven't take any photos. Try taking one by clicking the button below!",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(200.dp),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                if(state.predictions.isNotEmpty()) {
                    items(
                        state.predictions,

                        key = { prediction ->
                            prediction.id!!
                        }
                    ) { prediction ->
                        var bitmap: Bitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888)
                        bitmap.eraseColor(0xFF3a422f)

                        // this is heinous and probably really slow. I'll need to test in the future.
                        LaunchedEffect(prediction) {
                            val temp = viewModel.getBitmap(prediction)

                            if (temp != null) {
                                bitmap = temp
                            }
                        }

                        ImagesItem(
                            prediction = prediction,
                            bitmap = bitmap,
                            modifier = Modifier.clickable {
                                Toast.makeText(
                                    context,
                                    "Redirect to prediction/${prediction.id}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                    }
                }
            }
        }
    }
}