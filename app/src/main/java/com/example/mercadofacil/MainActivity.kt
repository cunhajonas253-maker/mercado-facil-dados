package com.example.mercadofacil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mercadofacil.ui.ShoppingListScreen
import com.example.mercadofacil.ui.theme.MercadoFacilTheme
import com.example.mercadofacil.data.AppDatabase
import com.example.mercadofacil.network.RetrofitClient
import com.example.mercadofacil.network.OffersApi
import com.example.mercadofacil.repository.ShoppingRepository
import com.example.mercadofacil.ui.ShoppingViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancia dependências simples (em produção use DI)
        val db = AppDatabase.getInstance(applicationContext)
        val offersApi = RetrofitClient.instance.create(OffersApi::class.java)
        val repo = ShoppingRepository(db, offersApi)

        viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ShoppingViewModel(repo) as T
            }
        }

        setContent {
            MercadoFacilTheme {
                val vm: ShoppingViewModel = viewModel(factory = viewModelFactory)
                ShoppingListScreen(viewModel = vm)
            }
        }
    }
}
