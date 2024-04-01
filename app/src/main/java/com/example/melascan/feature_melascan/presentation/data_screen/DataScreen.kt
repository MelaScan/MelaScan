package com.example.melascan.feature_melascan.presentation.data_screen

import NounAi1235933
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.domain.AI.ModelSizes
import com.example.melascan.feature_melascan.domain.model.BodyLocation
import com.example.melascan.feature_melascan.presentation.data_screen.components.EnumDropdown
import com.example.melascan.feature_melascan.presentation.util.components.CircleBackground
import com.example.melascan.feature_melascan.presentation.util.components.MelaTopBar
import kotlinx.coroutines.flow.StateFlow
import java.io.File

@Composable
fun DataScreen(
    navController: NavController,
    filename: String?,
    viewModel: DataViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uriImage = Uri.parse(filename?.replace('^', '/'))
    val bmp = ImageDecoder.createSource(context.contentResolver, uriImage).let {
        ImageDecoder.decodeBitmap(it)
    }
    Scaffold(
        topBar = {
            MelaTopBar(navController = navController, isIconVisible = false) {}
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Predict!", color=MaterialTheme.colorScheme.primary) },
                icon = { Icon(
                            NounAi1235933,
                            "ai brain",
                            modifier = Modifier.width(50.dp).height(50.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                },
                onClick = {
                    viewModel.onEvent(DataEvent.NavigateToAI { _ ->
                        Toast.makeText(context, "Navigate to AI prediction screen!", Toast.LENGTH_SHORT).show()
                    })
                },
                containerColor = MaterialTheme.colorScheme.tertiary,
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        CircleBackground()
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier
                .height(300.dp)
                .width(300.dp)) {
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "user's skin lesion photo",
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text("Fill out with your age below!", color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = if(viewModel.state.value.age == null) "" else { viewModel.state.value.age.toString() },
                onValueChange = {str ->
                    if (str.isDigitsOnly()) {
                        viewModel.onEvent(DataEvent.UpdatedAge(str.toInt()))
                    } else if(str.isEmpty()) {
                        viewModel.onEvent(DataEvent.UpdatedAge(null))
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                "Select the body location of the lesion",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            EnumDropdown(
                initialValue = BodyLocation.Torso.name,
                list = BodyLocation.entries,
                onClick = { index ->
                    viewModel.onEvent(DataEvent.UpdatedLocationDropdown(BodyLocation.entries[index]))
                },
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Select the size of the model you wish. The larger the model the longer it'll take to run and may increase in accuracy.",
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            EnumDropdown(
                initialValue = ModelSizes.Balanced.name,
                list = ModelSizes.entries,
                onClick = { index ->
                    viewModel.onEvent(DataEvent.UpdatedModelSize(ModelSizes.entries[index]))
            })

            Spacer(modifier = Modifier.height(50.dp))

        }
    }
}