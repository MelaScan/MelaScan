package com.example.melascan.feature_note.data.repository



import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository
import com.example.melascan.feature_note.data.data_source.PredictionDao
import com.example.melascan.feature_note.domain.model.Prediction
import kotlinx.coroutines.flow.Flow

class PredictionRepositoryImpl(
    private val dao: PredictionDao
): PredictionRepository {
    override fun getPredictions(): Flow<List<Prediction>> {
        return dao.getPredictions()
    }

    override suspend fun getPredictionById(id: Int): Prediction? {
        return dao.getPredictionById(id)
    }

    override suspend fun insertPrediction(prediction: Prediction) {
        dao.insertPrediction(prediction)
    }

    override suspend fun deletePrediction(prediction: Prediction) {
        dao.deletePrediction(prediction)
    }
}