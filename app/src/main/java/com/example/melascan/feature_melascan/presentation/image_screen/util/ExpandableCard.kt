package com.example.melascan.feature_melascan.presentation.image_screen.util

import androidx.collection.FloatList
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.melascan.feature_melascan.domain.model.Prediction

@Composable
fun ExpandableCard(
    expanded: Boolean,
    onClickExpanded: () -> Unit
) {
    val transition = updateTransition(targetState = expanded, label = "trans")

    val iconRotationDeg by
            transition.animateFloat(label = "icon change") { state ->
                if (state) {
                    0f
                } else {
                    180f
                }
            }

    Card(modifier = Modifier.fillMaxWidth(0.8f)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 20.dp)
                    .clickable {
                        onClickExpanded()
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "More Prediction Information")
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(iconRotationDeg)
                )
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun ExpandableContent(
    isExpanded: Boolean,
    strings: Array<String>,
    prediction: Prediction?
) {
    val percents = prediction?.let{
        arrayOf(
            it.percentAK,
            it.percentBCC,
            it.percentBKL,
            it.percentDF,
            it.percentSCC,
            it.percentVASC,
            it.percentNEVUS,
            it.percentOTHER
        )
    } ?: emptyArray()

    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut()
    }
    
    AnimatedVisibility(
        visible = isExpanded,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            strings.forEachIndexed { i, s ->
                PercentRow(
                    string = s,
                    percent = percents.getOrNull(i) ?: 0.0f,
                    spacerSize = if (i == 4) {
                        17
                    } else {
                        25
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
    
}

@Composable
fun PercentRow(string: String, percent: Float, spacerSize: Int = 25) {
    Spacer(modifier = Modifier.height(6.dp))
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            string,
            modifier = Modifier.align(Alignment.CenterVertically),
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.size(spacerSize.dp))
        Percent(percent = percent)
    }
    Spacer(modifier = Modifier.height(3.dp))
    Divider(modifier = Modifier.fillMaxWidth(0.65f), color = MaterialTheme.colorScheme.secondary)
}

@Composable
private fun Percent(percent: Float) {
    Box(
        modifier = Modifier
            .width(35.dp)
            .height(35.dp),
    ) {
        CircularProgressIndicator(
            progress = percent,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            textAlign = TextAlign.Center,
            text = "${(percent*100).toInt()}%",
            modifier = Modifier.align(Alignment.Center).size(25.dp).padding(vertical = 5.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

//@Preview
//@Composable
//fun PercentRowPreview() {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        PercentRow(string = "Percent Basal Cell Carcinoma", percent = 0.85f)
//    }
//}