package com.example.mercadofacil.network

import com.example.mercadofacil.network.dto.OfferDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OffersApi {
    @GET("offers")
    suspend fun getOffers(@Query("q") query: String): List<OfferDto>
}
