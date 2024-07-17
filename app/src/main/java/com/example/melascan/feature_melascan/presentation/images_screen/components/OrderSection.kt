package com.example.melascan.feature_melascan.presentation.images_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.melascan.feature_melascan.domain.util.PredictionsOrder

@Composable
fun OrderSection(
    modifier: Modifier,
    imagesOrder: PredictionsOrder,
    onOrderChange: (PredictionsOrder) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Date",
                checked = imagesOrder is PredictionsOrder.Date,
                onSelect = { onOrderChange(PredictionsOrder.Date(imagesOrder.orderType)) }
            )
            DefaultRadioButton(
                text = "Diagnosis: Melanoma",
                checked = imagesOrder is PredictionsOrder.DiagnosisMelanoma,
                onSelect = { onOrderChange(PredictionsOrder.DiagnosisMelanoma(imagesOrder.orderType)) }
            )
            DefaultRadioButton(
                text = "Diagnosis: Other",
                checked = imagesOrder is PredictionsOrder.DiagnosisMelanoma,
                onSelect = { onOrderChange(PredictionsOrder.DiagnosisOther(imagesOrder.orderType)) }
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                checked = imagesOrder.orderType is com.example.melascan.feature_melascan.domain.util.OrderType.Ascending,
                onSelect = {
                    onOrderChange(imagesOrder.copy(com.example.melascan.feature_melascan.domain.util.OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                checked = imagesOrder.orderType is com.example.melascan.feature_melascan.domain.util.OrderType.Descending,
                onSelect = {
                    onOrderChange(imagesOrder.copy(com.example.melascan.feature_melascan.domain.util.OrderType.Descending))
                }
            )
        }
    }
}