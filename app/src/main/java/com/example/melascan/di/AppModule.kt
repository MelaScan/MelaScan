package com.example.melascan.di

import android.app.Application
import androidx.room.Room
import com.example.melascan.feature_Prediction.domain.repository.PredictionRepository
import com.example.melascan.feature_melascan.data.data_source.prediction.PredictionDatabase
import com.example.melascan.feature_melascan.data.data_source.prompt.PromptDatabase
import com.example.melascan.feature_melascan.data.repository.PredictionRepositoryImpl
import com.example.melascan.feature_melascan.data.repository.PromptsRepositoryImpl
import com.example.melascan.feature_melascan.domain.repository.PromptsRepository
import com.example.melascan.feature_melascan.domain.use_case.prediction.AddPrediction
import com.example.melascan.feature_melascan.domain.use_case.prediction.DeletePrediction
import com.example.melascan.feature_melascan.domain.use_case.prediction.GetLatestPrediction
import com.example.melascan.feature_melascan.domain.use_case.prediction.GetPrediction
import com.example.melascan.feature_melascan.domain.use_case.prediction.GetPredictions
import com.example.melascan.feature_melascan.domain.use_case.prediction.NukePredictions
import com.example.melascan.feature_melascan.domain.use_case.prediction.PredictionUseCases
import com.example.melascan.feature_melascan.domain.use_case.prompts.AddPrompts
import com.example.melascan.feature_melascan.domain.use_case.prompts.GetPrompts
import com.example.melascan.feature_melascan.domain.use_case.prompts.PromptsUseCases
import com.example.melascan.feature_melascan.domain.use_case.prompts.SetPrompts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// I have no idea what half of this does. If it works that'll be a miracle.
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePredictionDatabase(app: Application): PredictionDatabase =
        Room.databaseBuilder(
            app,
            PredictionDatabase::class.java,
            PredictionDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun providePredictionRepository(db: PredictionDatabase): PredictionRepository =
        PredictionRepositoryImpl(db.predictionDao)


    @Provides
    @Singleton
    fun providePredictionUseCases(repository: PredictionRepository): PredictionUseCases {
        return PredictionUseCases(
            addPrediction = AddPrediction(repository),
            deletePrediction = DeletePrediction(repository),
            getPrediction = GetPrediction(repository),
            getPredictions = GetPredictions(repository),
            nukePredictions = NukePredictions(repository),
            getLatestPrediction = GetLatestPrediction(repository),
        )
    }

    @Provides
    @Singleton
    fun providePromptDatabase(app: Application): PromptDatabase =
        Room.databaseBuilder(
            app,
            PromptDatabase::class.java,
            PromptDatabase.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun providePromptRepository(db: PromptDatabase): PromptsRepository =
        PromptsRepositoryImpl(db.promptDao)

    @Provides
    @Singleton
    fun providePromptsUseCases(repository: PromptsRepository): PromptsUseCases {
        return PromptsUseCases(
            addPrompt = AddPrompts(repository),
            setPrompts = SetPrompts(repository),
            getPrompts = GetPrompts(repository)
        )
    }

}