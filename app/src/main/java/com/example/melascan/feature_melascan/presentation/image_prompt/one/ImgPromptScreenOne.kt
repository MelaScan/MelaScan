package com.example.melascan.feature_melascan.presentation.image_prompt.one

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.image_prompt.ImgPromptScaffold

@Composable
fun ImgPromptScreenOne(navController: NavController) {
    ImgPromptScaffold(navController = navController,) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Don't be dumb with the photo",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
    }
}