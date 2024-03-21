package com.example.melascan.feature_melascan.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}