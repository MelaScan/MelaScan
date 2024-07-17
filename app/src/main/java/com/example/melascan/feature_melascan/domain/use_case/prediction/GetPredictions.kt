package com.example.melascan.feature_melascan.domain.use_case.prediction

import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository
import com.example.melascan.feature_melascan.domain.model.Prediction
import com.example.melascan.feature_melascan.domain.util.OrderType
import com.example.melascan.feature_melascan.domain.util.PredictionsOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPredictions(
    private val repository: PredictionRepository
) {
    operator fun invoke(
        predictionsOrder: PredictionsOrder = PredictionsOrder.Date(OrderType.Descending)
    ): Flow<List<Prediction>> {
        return repository.getPredictions().map { predictions ->
            when(predictionsOrder.orderType) {
                is OrderType.Ascending -> {
                    when(predictionsOrder) {
                        is PredictionsOrder.Date -> predictions.sortedBy { it.timestamp }
                        is PredictionsOrder.DiagnosisMelanoma -> predictions.sortedBy { it.percentMelanoma }
                        is PredictionsOrder.DiagnosisOther -> predictions.sortedBy { it.percentOTHER }
                    }
                }
                is OrderType.Descending -> {
                    when(predictionsOrder) {
                        is PredictionsOrder.Date -> predictions.sortedByDescending { it.timestamp }
                        is PredictionsOrder.DiagnosisMelanoma -> predictions.sortedByDescending { it.percentMelanoma }
                        is PredictionsOrder.DiagnosisOther -> predictions.sortedByDescending { it.percentOTHER }
                    }
                }
            }
        }
    }
}