package com.example.melascan.feature_melascan.presentation.image_prompt.one

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.melascan.R
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.feature_melascan.presentation.util.components.CircleBackground
import com.example.melascan.feature_melascan.presentation.util.components.MelaTopBar
import kotlinx.coroutines.launch

val imgPromptMap: HashMap<Int, @Composable (NavController) -> Unit> = hashMapOf(
    0 to {
        promptSVGDisplay(id = R.drawable.close_up_prompt) {
            Text(
                "Make sure the photo of the lesion is up close to the camera and centered!",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    },
    1 to {
        promptSVGDisplay(id = R.drawable.light_prompt) {
            Text(
                "Make sure the photo is well lit and visible.",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    },
    2 to {
        val circleColor = MaterialTheme.colorScheme.primary
        promptSVGDisplay(id = R.drawable.phone_prompt, it) {
            Text("Good Luck on the photo!", color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(30.dp))
            IconButton(onClick = {
                // this should crash rn
                it?.navigate(Screen.TakePhoto.route)
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .height(40.dp)
                .width(40.dp)
                .drawBehind {
                    translate(left = 50f, top = -45f) {
                        drawCircle(circleColor, radius = 7.dp.toPx())
                    }
                    translate(left = -50f, top = 35f) {
                        drawCircle(circleColor, radius = 5.dp.toPx())
                    }
                    translate(left = 45f, top = 32.5f) {
                        drawCircle(circleColor, radius = 4.dp.toPx())
                    }
                }) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
)

@Composable
fun promptSVGDisplay(id: Int, navController: NavController? = null, compose: (@Composable (NavController?) -> Unit)) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = Modifier
            .width(300.dp)
            .height(300.dp)
    )
    Spacer(modifier = Modifier.height(30.dp))
    compose.invoke(navController)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImgPromptScreenOne(navController: NavController) {
    val pagerState = rememberPagerState(pageCount = {
        3
    })

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MelaTopBar(navController = navController, isIconVisible = false) {}
        }
    ) {
        CircleBackground()
        Column(
            modifier = Modifier
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HorizontalPager(state = pagerState) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight(0.95f)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xCCD7DBD2)
                    )
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        imgPromptMap[it]?.invoke(navController)
                        Spacer(modifier = Modifier.height(30.dp))
                        if (it != 2) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowRightAlt,
                                contentDescription = "Right Arrow",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .scale(1.5f)
                                    .clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                        }
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}