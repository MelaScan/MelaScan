package com.example.melascan.feature_melascan.presentation.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.melascan.feature_melascan.presentation.util.components.MelaScaffold
import com.example.melascan.feature_melascan.presentation.util.components.isTablet
import com.example.melascan.feature_melascan.presentation.util.fonts.ibarra_real
import com.example.melascan.feature_melascan.presentation.util.fonts.sree_k

// hard coding the data for now.
// we'll move to android resources later

val typesOfSkinCancer: List<String> = listOf(
    "Bowen's Disease",
    "Melanoma",
    "Basal Cell Carcinoma",
)

val infoOfSkinCancer: Map<String, String> = mapOf(
    "Bowen's Disease" to "An early form of skin cancer that is very slow-growing, with cancer cells only located in the outermost layer of skin, the epidermis.",
    "Melanoma" to "Melanoma is skin cancer originating from the melanocytes, which give color to the skin (melanin). Melanoma is typically caused by sun exposure.",
    "Basal Cell Carcinoma" to "A type of skin cancer that starts in the basal cells (cells that produce new skin cells) and appears as a white waxy lump or brown scaly patch on sun-exposed areas."
)


val typesOfBenignSkinGrowths: List<String> = listOf(
    "Benign Keratosis-like Lesions",
    "Dermatofibroma",
    "Melanocytic Nevi",
    "Vascular Lesions",
)

val infoOfBenignSkinGrowths: Map<String, String> = mapOf(
    "Benign Keratosis-like Lesions" to "While not cancerous, benign keratosis-like lesions grow slowly and thicken over time, causing discomfort and pain for some people.",
    "Dermatofibroma" to "A benign tumor, dermatofibroma is a skin nodule found in the second layer of the skin, the dermis.",
    "Melanocytic Nevi" to "Commonly known as a mole, melanocytic nevi are benign (non-cancerous) birthmarks made of melanocytes.",
    "Vascular Lesions" to " Vascular Lesions are common birth marks of the skin and underlying tissue that can be divided into 3 categories: Hemangiomas, Vascular Malformations, and Pyogenic Granulomas. Hemangiomas are extra blood vessels, vascular malformation is abnormal formation of blood vessels, and pyogenic granulomas are  small, raised, and red bumps on the skin filled with blood vessels."
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
) {
    val scope = rememberCoroutineScope()

    val openDialog = remember { mutableStateOf(false) }
    // true == skin cancer, false == benign skin growths
    val dialogType = remember { mutableStateOf(false) }
    val infoType = remember { mutableStateOf("") }

    val isTablet = isTablet()

    val scrollState = rememberScrollState()
    MelaScaffold(navController = navController, scope = scope) { paddingValues ->
        if (openDialog.value) {
            Dialog(onDismissRequest = { openDialog.value = false }) {
                val text = if (dialogType.value) { infoOfSkinCancer[infoType.value]!! } else { infoOfBenignSkinGrowths[infoType.value]!! }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            if (text.length <= 129) 250.dp else {
                                if (text.length > 165) 400.dp else 300.dp
                            }
                        )
                        .padding(10.dp),
                    shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.primary,
                    )
                ) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = infoType.value,
                            fontFamily = ibarra_real,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(color = MaterialTheme.colorScheme.tertiary)
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = ibarra_real,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(50.dp),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Text(
                text = "MelaScan aims to make skin cancer diagnoses more accessible by creating a device that uses AI-based monitoring to analyze images the user inputs of their suspicious moles at the comfort of one’s home.\n",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary,
            )

            Divider()
            Text(
                text="Note that this is not professional medical advice. Any concerns about your skin should be addressed by a professional dermatologist.",
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = ibarra_real,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )
            Divider()

            // Types of Skin Cancer
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .background(Color(0xFF949F7F))
                    .width(
                        if (isTablet) {
                            600.dp
                        } else 300.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Types of Skin Cancer",
                    Modifier.padding(10.dp),
                    fontFamily = sree_k,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF3A422F)
                )
                Divider(color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.width(100.dp))
                Spacer(modifier = Modifier.height(30.dp))

                typesOfSkinCancer.forEach { type ->
                        InfoRow(type) {
                            openDialog.value = true
                            dialogType.value = true
                            infoType.value = type
                        }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                    Spacer(modifier = Modifier.height(10.dp))
            } // column

            // Types of Benign Skin Growths
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
                    .background(Color(0xFF949F7F))
                    .width(
                    if (isTablet) {
                        600.dp
                    } else 300.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Text(
                        "Types of Benign Growths",
                        modifier = Modifier.padding(10.dp),
                        fontFamily = sree_k,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF3A422F)
                    )
                Divider(color = MaterialTheme.colorScheme.tertiary, modifier = Modifier.width(100.dp))
                Spacer(modifier = Modifier.height(30.dp))

                typesOfBenignSkinGrowths.forEach { type ->
                    InfoRow(type) {
                        openDialog.value = true
                        dialogType.value = false
                        infoType.value = type
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
        } // column
    } // MeloScaffold
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
            tint = Color.Black,
            modifier = Modifier
                .weight(0.25f)
        )
        Text(text, modifier = Modifier.weight(0.5f), color = Color.Black)
    }
}

@Composable
fun InfoRow(text: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth(0.75f)
            .clickable {
                onClick()
            }) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = "Info",
            tint = Color.Black,
            modifier = Modifier.weight(0.5f)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontFamily = ibarra_real,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF3A422F),
            modifier = Modifier
                .weight(0.75f)
        )
    }
    Divider(modifier = Modifier.width(200.dp), color = MaterialTheme.colorScheme.tertiary)
}


