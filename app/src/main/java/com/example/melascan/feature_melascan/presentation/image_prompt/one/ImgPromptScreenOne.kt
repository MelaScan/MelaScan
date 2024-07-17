package com.example.melascan.feature_melascan.presentation.image_prompt.one

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.CameraAlt
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
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
        PromptSVGDisplay(id = R.drawable.close_up_prompt) {
            Text(
                stringResource(id = R.string.prompt_map_0),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    },
    1 to {
        PromptSVGDisplay(id = R.drawable.light_prompt) {
            Text(
                stringResource(id = R.string.prompt_map_1),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    },
    2 to {
        val circleColor = MaterialTheme.colorScheme.primary
        PromptSVGDisplay(id = R.drawable.phone_prompt, it) {
            Text(stringResource(id = R.string.prompt_map_2), color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(30.dp))
            IconButton(onClick = {
                it?.navigate(Screen.ImageSelectScreen.route)
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .scale(1.5f)
                .drawBehind {
                    translate(left = 13.dp.toPx(), top = -15.dp.toPx()) {
                        drawCircle(circleColor, radius = 7.dp.toPx())
                    }
                    translate(left = -16.dp.toPx(), top = 14.dp.toPx()) {
                        drawCircle(circleColor, radius = 5.dp.toPx())
                    }
                    translate(left = 17.dp.toPx(), top = 11.dp.toPx()) {
                        drawCircle(circleColor, radius = 4.dp.toPx())
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
)

@Composable
fun PromptSVGDisplay(id: Int, navController: NavController? = null, compose: (@Composable (NavController?) -> Unit)) {
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
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