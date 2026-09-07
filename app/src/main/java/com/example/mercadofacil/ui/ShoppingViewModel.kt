package com.example.mercadofacil.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercadofacil.data.ShoppingItem
import com.example.mercadofacil.repository.ShoppingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repo: ShoppingRepository) : ViewModel() {
    val items: StateFlow<List<ShoppingItem>> = repo.getItems()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val totalEstimated = items.map { list ->
        list.map { (it.estimatedPrice ?: 0.0) * it.quantity }.sum()
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    fun addItem(name: String, price: Double?) {
        viewModelScope.launch {
            val item = ShoppingItem(name = name, estimatedPrice = price)
            repo.addItem(item)
        }
    }

    fun deleteItem(item: ShoppingItem) {
        viewModelScope.launch {
            repo.deleteItem(item)
        }
    }

    fun fetchOffers(query: String, onComplete: (Int) -> Unit) {
        viewModelScope.launch {
            val offers = repo.fetchOffersFor(query)
            onComplete(offers.size)
        }
    }
}
