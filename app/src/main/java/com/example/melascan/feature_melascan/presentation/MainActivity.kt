package com.example.melascan.feature_melascan.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose.MelaScanTheme
import com.example.melascan.feature_melascan.presentation.camera_screen.CameraScreen
import com.example.melascan.feature_melascan.presentation.company_screen.CompanyScreen
import com.example.melascan.feature_melascan.presentation.data_screen.DataScreen
import com.example.melascan.feature_melascan.presentation.data_screen.DataState
import com.example.melascan.feature_melascan.presentation.home_screen.HomeScreen
import com.example.melascan.feature_melascan.presentation.image_prompt.one.ImgPromptScreenOne
import com.example.melascan.feature_melascan.presentation.image_screen.ImageScreen
import com.example.melascan.feature_melascan.presentation.images_screen.ImagesScreen
import com.example.melascan.feature_melascan.presentation.photo_upload_screen.PhotoUploadScreen
import com.example.melascan.feature_melascan.presentation.prediction_screen.PredictionScreen
import com.example.melascan.feature_melascan.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        setContent {
            MelaScanTheme {
                navController = rememberNavController()

                var dataState: DataState? = null

                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    composable(
                        route = Screen.HomeScreen.route,
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    300, easing = LinearEasing
                                )
                            )
                        },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(
                                    300, easing = LinearEasing
                                )
                            )
                        }
                    ) {
                        HomeScreen(navController = navController)
                    }
                    composable(
                        route = Screen.ImageScreen.route,
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    300, easing = LinearEasing
                                )
                            )
                        },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(
                                    300, easing = LinearEasing
                                )
                            )
                        }
                    ) {
                        ImagesScreen(navController = navController)
                    }

                    composable(
                        route = Screen.CompanyScreen.route,
                        enterTransition = {
                            fadeIn(
                                animationSpec = tween(
                                    300, easing = LinearEasing
                                )
                            )
                        },
                        exitTransition = {
                            fadeOut(
                                animationSpec = tween(
                                    300, easing = LinearEasing
                                )
                            )
                        }
                    ) {
                        CompanyScreen(navController = navController)
                    }
                    composable(
                        route = Screen.ImagePromptScreens.One.route
                    ) {
                        ImgPromptScreenOne(navController = navController)
                    }
                    composable(
                        route = Screen.TakePhoto.route
                    ) {
                        CameraScreen(navController = navController)
                    }
                    composable(
                        route = "${Screen.DataInput.route}/{filename}",
                        arguments = listOf(
                            navArgument("filename") {
                                type = NavType.StringType
                            }
                        )
                    ) { entry ->
                        val filename = entry.arguments?.getString("filename")
                        DataScreen(navController = navController, filename, {
                            dataState = it
                        })
                    }
                    composable(
                        route = "${Screen.PredictionScreen.route}/{filename}",
                        arguments = listOf(
                            navArgument("filename") {
                                type = NavType.StringType
                            }
                        )
                    ) {entry ->
                        val filename = entry.arguments?.getString("filename")
                        PredictionScreen(
                            navController = navController,
                            fileName = filename,
                            dataState = dataState!!
                        )
                    }
                    composable(
                        route = "${Screen.ImageViewScreen.route}/{id}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                            }
                        )
                    ) { entry ->
                        val id = entry.arguments?.getInt("id")
                        ImageScreen(navController = navController, id!!)
                    }
                    composable(
                        route = Screen.ImageSelectScreen.route,
                    ) {
                        PhotoUploadScreen(navController = navController)
                    }
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
        )
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}