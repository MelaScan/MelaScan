package com.example.melascan.feature_melascan.presentation.images_screen

import NounHidden598316
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.melascan.R
import com.example.melascan.feature_melascan.presentation.images_screen.components.ImagesItem
import com.example.melascan.feature_melascan.presentation.images_screen.components.OrderSection
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold

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
                    //Toast.makeText(context, "Redirect to Photo Prompt!", Toast.LENGTH_SHORT).show()
                    viewModel.onEvent(ImagesEvent.SetPromptTrue)
                    navController.navigate(Screen.ImagePromptScreens.One.route)
                } else {
                    //Toast.makeText(context, "Redirect to Photo!", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.ImageSelectScreen.route)
                }
            },
            icon = { Icon(Icons.Filled.CameraAlt, "Camera action button") },
            text = { Text(text = stringResource(id = R.string.images_FAB)) },
            containerColor = MaterialTheme.colorScheme.secondary
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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
                    text = stringResource(id = R.string.images_sort_title),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                IconButton(onClick = {
                    viewModel.onEvent(ImagesEvent.ToggleOrderSection)
                },) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Sort,
                        contentDescription = "Sort",
                        tint = MaterialTheme.colorScheme.tertiary
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
                    stringResource(id = R.string.images_empty_prompt),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            /**/
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
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

                        if(prediction.isHidden) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .height(prediction.height.dp)
                                    .clickable {
                                        navController.navigate("${Screen.ImageViewScreen.route}/${prediction.id ?: 1}")
                                    }
                            ) {
                                Icon(
                                    imageVector = NounHidden598316,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxSize()
                                )
                                Text(
                                    stringResource(id = R.string.images_hidden_prompt),
                                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                )
                            }
                        } else {

                            var bitmap = viewModel.getBitmap(context, prediction)

                            LaunchedEffect(viewModel.state.value.bitmaps) {
                                bitmap = viewModel.state.value.bitmaps[prediction.id!!]
                            }

                            ImagesItem(
                                prediction = prediction,
                                bitmap = bitmap,
                                modifier = Modifier
                                    .height(prediction.height.dp)
                                    .clickable {
                                        navController.navigate("${Screen.ImageViewScreen.route}/${prediction.id ?: 1}")
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}