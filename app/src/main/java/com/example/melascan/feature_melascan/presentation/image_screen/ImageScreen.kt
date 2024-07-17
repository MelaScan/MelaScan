package com.example.melascan.feature_melascan.presentation.image_screen

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.melascan.R
import com.example.melascan.feature_melascan.domain.model.BodyLocation
import com.example.melascan.feature_melascan.presentation.image_screen.util.ExpandableCard
import com.example.melascan.feature_melascan.presentation.image_screen.util.ExpandableContent
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold
import java.util.Calendar
import java.util.TimeZone

@Composable
fun ImageScreen(
    navController: NavController,
    id: Int,
    imageViewModel: ImageViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val predictionStrings = context.resources.getStringArray(R.array.image_prediction_strings)
    imageViewModel.setup(id)

    var bmp: Bitmap = imageViewModel.state.value?.let {
        imageViewModel.getBitmap(context, it)
    } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    var formatDate = getTime(imageViewModel.state.value?.timestamp) ?: "N/A" //

    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(imageViewModel.state.value) {
        bmp = imageViewModel.state.value?.let {
            imageViewModel.getBitmap(context, it)
        } ?: Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

        formatDate = getTime(imageViewModel.state.value?.timestamp) ?: "N/A"
    }
    val melanoma = stringResource(id = R.string.melanoma)
    val scrollState = rememberScrollState()
    MelaScaffold(navController = navController, scope = coroutineScope) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
            ) {
                Image(
                    bitmap = bmp.asImageBitmap(),
                    contentDescription = "user's skin lesion photo",
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
            ) {
                    CircularProgressIndicator(
                        progress = imageViewModel.state.value?.percentMelanoma ?: 0f,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "${((imageViewModel.state.value?.percentMelanoma ?: 0f) * 100).toInt()}% $melanoma",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        textAlign = TextAlign.Center,
                        fontWeight = MaterialTheme.typography.bodySmall.fontWeight,
                    )
            }

            Text(
                text = stringResource(id = R.string.image_more_info),
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(10.dp),
                fontWeight = FontWeight.Bold,
                fontSize  = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "-Body Location: ${(BodyLocation.entries[imageViewModel.state.value?.anomSiteGeneral ?: 0]).name.replace("(.)([A-Z])".toRegex(), "$1 $2")}",
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
            )
            Text(
                text = "-Age: ${imageViewModel.state.value?.ageApprox}",
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
            )
            // annotated string to style the text
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    ) {
                        append("-Date: ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    ) {
                        append(formatDate)
                    }
                },
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            ExpandableCard(expanded = isExpanded) {
                isExpanded = !isExpanded
            }
            ExpandableContent(isExpanded = isExpanded, strings = predictionStrings, prediction = imageViewModel.state.value)

            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    ) {
                        append("Click ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            textDecoration = TextDecoration.Underline
                        ),
                    ) {
                        append("here")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize
                        )
                    ) {
                        append(
                            " to find out more from a local dermatologist and be sure to ask regarding your photo!"
                        )
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clickable {
                        val gmmIntentUri = Uri.parse("geo:0,0?q=dermatologist")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        startActivity(context, mapIntent, null)
                    },
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

fun getTime(long: Long?): String? {
    if(long == null) return null
    val cal: Calendar = Calendar.getInstance()
    cal.setTimeZone(TimeZone.getDefault())
    cal.setTimeInMillis(long)
    return "${cal.get(Calendar.MONTH)+1}/${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.YEAR)}"
}