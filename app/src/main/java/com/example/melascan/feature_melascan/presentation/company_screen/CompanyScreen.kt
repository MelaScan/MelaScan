package com.example.melascan.feature_melascan.presentation.company_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold
import com.example.melascan.feature_melascan.presentation.util.fonts.ibarra_real

@Composable
fun CompanyScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    MelaScaffold(navController = navController, scope = scope) { padding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "Our Mission",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "MelaScan’s mission is to make screening for skin cancer more accessible, decreasing the need for expensive appointments with specialists through our app, which uses AI to screen suspicious skin lesions \nfor potential skin cancer.",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                "People",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(padding),
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                "Imagine some cool sketches of each of us or photos of each of us \uD83D\uDE0E",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}