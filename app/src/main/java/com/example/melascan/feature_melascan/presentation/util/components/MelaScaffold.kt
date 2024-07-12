package com.example.melascan.feature_melascan.presentation.util.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MelaScaffold(navController: NavController, scope: CoroutineScope, onTopBarClick: (() -> Unit)? = null, floatingActionButton: (@Composable () -> Unit)? = null, compose: @Composable (paddingValues: PaddingValues,) -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    Scaffold(
        topBar = {
            MelaTopBar(navController = navController) {
                onTopBarClick?.invoke()
                scope.launch {
                    if (drawerState.isOpen) {
                        drawerState.close()
                    } else if (drawerState.isClosed) {
                        drawerState.open()
                    }
                }
            }
        },
        floatingActionButton = {
            // not every screen has a floating action button
            floatingActionButton?.invoke()
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        CircleBackground()
        ModalNavigationDrawer(
            drawerContent = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(if (isPortrait()) 0.44f else 0.25f)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(it)
                        .clip(RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp)),
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    DrawerRow(
                        imageVector = Icons.Outlined.Home,
                        text = "Home",
                    ) {
                        navController.navigate(Screen.HomeScreen.route)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    DrawerRow(
                        imageVector = Icons.Outlined.Image,
                        text = "Images",
                    ) {
                        navController.navigate(Screen.ImageScreen.route)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    DrawerRow(
                        imageVector = Icons.Outlined.AccountCircle,
                        text = "Our Company",
                    ) {
                        navController.navigate(Screen.CompanyScreen.route)
                    }
                }
            },
            drawerState = drawerState,
        ) {
            compose(it)
        }
    }

}

@Composable
fun DrawerRow(imageVector: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick),
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .weight(0.25f)
        )
        Text(text, modifier = Modifier.weight(0.5f), color = MaterialTheme.colorScheme.secondary)
    }
}