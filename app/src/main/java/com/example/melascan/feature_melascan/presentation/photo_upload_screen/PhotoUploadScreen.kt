package com.example.melascan.feature_melascan.presentation.photo_upload_screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Girl
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.feature_melascan.presentation.util.components.CircleBackground
import com.example.melascan.feature_melascan.presentation.util.components.MelaTopBar

@Composable
fun NavRow(
    imgVector: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Row(modifier = Modifier
        .padding(horizontal = 75.dp)
        .clickable {
            onClick()
        },
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = imgVector, contentDescription = text, tint = MaterialTheme.colorScheme.primary)
        Text(
            modifier = Modifier.weight(0.7f),
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
    Spacer(modifier = Modifier.height(15.dp))
    Divider(Modifier.fillMaxWidth(0.5f))
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun PhotoUploadScreen(navController: NavController,) {
    var uriStr by remember { mutableStateOf("") }
    val pickMedia = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
        uri?.let {
            uriStr = it.toString()
        }
    }
    LaunchedEffect(uriStr) {
        if (uriStr != "") {
            navController.navigate("${Screen.DataInput.route}/${uriStr.replace('/', '^')}")
        }
    }

    Scaffold(
        topBar = { 
            MelaTopBar(navController = navController, isIconVisible = false) {} 
        }
    ) { paddingValues ->
        CircleBackground()
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(75.dp))
            NavRow(Icons.Default.CameraAlt, "Take Photo") {
                navController.navigate(Screen.TakePhoto.route)
            }
            Spacer(modifier = Modifier.height(20.dp))
            NavRow(imgVector = Icons.Default.Upload, text = "Upload photo from device") {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            Spacer(modifier = Modifier.height(20.dp))
            NavRow(imgVector = Icons.Default.Lightbulb, text = "Reminder on how photos should look") {
                navController.navigate(Screen.ImagePromptScreens.One.route)
            }
        }
    }
}