package com.example.mercadofacil.repository

import com.example.mercadofacil.data.AppDatabase
import com.example.mercadofacil.data.ShoppingItem
import com.example.mercadofacil.network.OffersApi
import com.example.mercadofacil.network.dto.OfferDto
import kotlinx.coroutines.flow.Flow

class ShoppingRepository(
    private val db: AppDatabase,
    private val offersApi: OffersApi
) {
    fun getItems(): Flow<List<ShoppingItem>> = db.shoppingDao().getAll()

    suspend fun addItem(item: ShoppingItem) = db.shoppingDao().insert(item)

    suspend fun updateItem(item: ShoppingItem) = db.shoppingDao().update(item)

    suspend fun deleteItem(item: ShoppingItem) = db.shoppingDao().delete(item)

    suspend fun fetchOffersFor(query: String): List<OfferDto> {
        return try {
            offersApi.getOffers(query)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
