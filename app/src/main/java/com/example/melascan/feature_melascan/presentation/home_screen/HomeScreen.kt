package com.example.melascan.feature_melascan.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold
import com.example.melascan.feature_melascan.presentation.util.fonts.ibarra_real

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    MelaScaffold(navController = navController, scope = scope) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "MelaScan aims to make skin cancer diagnoses more accessible by creating a device that uses AI-based monitoring to analyze the user's worrisome lesions from the comfort of one’s home.\n",
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
                text="Note that this is not professional medical advice. Any concerns about your skin should be addressed by a professional dermatologist.",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Divider()

            Spacer(modifier = Modifier.height(80.dp))
        } // column
    } // MeloScaffold
}


