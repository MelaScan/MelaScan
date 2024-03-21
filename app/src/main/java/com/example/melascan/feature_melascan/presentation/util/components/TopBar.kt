package com.example.melascan.feature_melascan.presentation.util.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.util.Screen
import com.example.melascan.feature_melascan.presentation.util.fonts.sree_k

// can't use "TopBar" as the name of the composable function because it's already used in the Material3 library
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MelaTopBar(navController: NavController, isIconVisible: Boolean = true, onTopClicked: () -> Unit) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        title = {
            MelaTopText(navController)
        },
        navigationIcon = {
            if (isIconVisible) {
                IconButton({ onTopClicked() }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu",
                    )
                }
            }
        },
    )
}

@Composable
fun MelaTopText(navController: NavController) {
    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(
            color = MaterialTheme.colorScheme.secondary, fontFamily = sree_k,
            fontWeight = FontWeight.Normal,
            fontSize = 30.sp),
        ) {
            append("MELA")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary, fontFamily = sree_k,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp)
        ) {
            append("SCAN")
        }
    },
    modifier = Modifier.clickable {
        navController.navigate(Screen.HomeScreen.route)
    })
}