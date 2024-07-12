package com.example.melascan.feature_melascan.presentation.home_screen

import NounArrow5953741
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.melascan.R
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold
import com.example.melascan.feature_melascan.presentation.util.fonts.ibarra_real

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()

    var isDrawerOpen by remember { mutableStateOf(false) }

    MelaScaffold(navController = navController, scope = scope, onTopBarClick = {
        if (!isDrawerOpen) {
            homeViewModel.setPromptsTrue()
        }
        isDrawerOpen = true
    }) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = stringResource(id = R.string.mission_statement),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
            )

            Spacer(modifier = Modifier.height(60.dp))

            Divider()
            Text(
                text= stringResource(id = R.string.disclaimer),
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Divider()

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                "Go to images?",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.height(15.dp))
            
            val circleColor = MaterialTheme.colorScheme.primary
            IconButton(onClick = {
                navController.navigate(Screen.ImageScreen.route)
            },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .scale(1.5f)
                    .drawBehind {
                        translate(left = 50f, top = -45f) {
                            drawCircle(circleColor, radius = 7.dp.toPx())
                        }
                        translate(left = -58f, top = 25f) {
                            drawCircle(circleColor, radius = 5.dp.toPx())
                        }
                        translate(left = 45f, top = 52.5f) {
                            drawCircle(circleColor, radius = 4.dp.toPx())
                        }
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Image,
                    null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        } // column
    } // MeloScaffold

    if (!isDrawerOpen && homeViewModel.state.value.prompts?.promptedTopBar == false) {
        Box(modifier = Modifier.fillMaxSize()) {

            Icon(
                imageVector = NounArrow5953741,
                "arrow",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(vertical = 50.dp, horizontal = 25.dp)
                    .size(45.dp)
            )
            Text(
                "Psst images and app info is here!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(vertical = 83.dp, horizontal = 55.dp)
            )
        }
    }
}


