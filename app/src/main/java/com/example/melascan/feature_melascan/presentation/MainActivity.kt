package com.example.melascan.feature_melascan.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.melascan.feature_melascan.presentation.company_screen.CompanyScreen
import com.example.melascan.feature_melascan.presentation.home_screen.HomeScreen
import com.example.melascan.feature_melascan.presentation.image_prompt.one.ImgPromptScreenOne
import com.example.melascan.feature_melascan.presentation.images_screen.ImagesScreen
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.ui.theme.MelaScanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MelaScanTheme {
                val navController = rememberNavController()
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

                }
            }
        }
    }
}