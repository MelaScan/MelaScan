package com.example.melascan.feature_melascan.domain.util

// We'll add more ways to order the predictions in the future. For now it's based off of date alone
sealed class PredictionsOrder(val orderType: OrderType) {

    class Date(orderType: OrderType) : PredictionsOrder(orderType)

    class Diagnosis(orderType: OrderType): PredictionsOrder(orderType)

    fun copy(orderType: OrderType): PredictionsOrder {
        return when(this) {
            is Date -> Date(orderType)
            is Diagnosis -> Diagnosis(orderType)
        }
    }

}