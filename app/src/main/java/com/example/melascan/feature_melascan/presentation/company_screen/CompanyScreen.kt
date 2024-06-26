package com.example.melascan.feature_melascan.presentation.company_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.melascan.R
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold
import com.example.melascan.feature_melascan.presentation.util.fonts.ibarra_real
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

val list: List<FounderData> = listOf(
    FounderData(
        imgInt = R.drawable.tanvi,
        name = "Tanvi Saxena",
        title = "Project Lead and Head Artist",
        description = "Designed every screen and piece of art while also guiding and managing the project."
    ),
    FounderData(
        imgInt = R.drawable.neha,
        name = "Neha Vivek",
        title = "Health and Outreach Lead",
        description = "Ensured datasets were representative of the user base and ensured the app would appropriately inform the user."
    ),
    FounderData(
        imgInt = R.drawable.caleb,
        name = "Caleb Cleavinger",
        title = "Head SWE and AI/ML developer",
        description = "Trained and developed the ensemble models used by MelaScan and programmed the app itself."
    ),
)

@Composable
fun DrawFounder(founderData: FounderData) {
    Text(
        text = founderData.name,
        style = MaterialTheme.typography.titleLarge,
        fontFamily = ibarra_real,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.secondary
    )
    Image(
        painter = painterResource(id = founderData.imgInt),
        contentDescription = "Founder Image of ${founderData.name}",
        modifier = Modifier
            .height(200.dp)
            .width(200.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
    )
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = founderData.title,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = ibarra_real,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = founderData.description,
        style = MaterialTheme.typography.bodyLarge,
        fontFamily = ibarra_real,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(10.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CompanyScreen(
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState {
        list.size
    }

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
                text = "We want to democratize healthcare and skincare through advanced AI techniques on your own device.",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "Creators of MelaScan",
                style = MaterialTheme.typography.titleLarge,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(padding),
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(60.dp))

            HorizontalPager(state = pagerState) {
                Card(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xCCD7DBD2)
                    )
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        DrawFounder(
                            founderData = list[
                                it
                            ] // creates a circular list effect
                        )
                        Row {
                            IconButton(onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        if(pagerState.currentPage == 0) {
                                            list.size - 1
                                        } else {
                                            pagerState.currentPage - 1
                                        }
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowLeft,
                                    contentDescription = "Left Arrow",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .scale(1.5f)
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            IconButton(onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        if (pagerState.currentPage == list.size-1) {
                                            0
                                        } else {
                                            pagerState.currentPage + 1
                                        }
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                    contentDescription = "Right Arrow",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .scale(1.5f)
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}