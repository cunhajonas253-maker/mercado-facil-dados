package com.example.mercadofacil.network.dto

data class OfferDto(
    val id: String,
    val productName: String,
    val storeName: String,
    val price: Double,
    val unit: String,
    val timestamp: String
)
