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
    /* TODO: add more ways of sorting results (i.e by photos with highest score in certain predictions) */
    operator fun invoke(
        predictionsOrder: PredictionsOrder = PredictionsOrder.Date(OrderType.Descending)
    ): Flow<List<Prediction>> {
        return repository.getPredictions().map { predictions ->
            when(predictionsOrder.orderType) {
                is OrderType.Ascending -> {
                    when(predictionsOrder) {
                        is PredictionsOrder.Date -> predictions.sortedBy { it.timestamp }
                    }
                }
                is OrderType.Descending -> {
                    when(predictionsOrder) {
                        is PredictionsOrder.Date -> predictions.sortedByDescending { it.timestamp }
                    }
                }
            }
        }
    }
}