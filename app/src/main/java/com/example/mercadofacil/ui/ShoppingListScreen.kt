package com.example.mercadofacil.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mercadofacil.data.ShoppingItem
import kotlinx.coroutines.flow.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete

@Composable
fun ShoppingListScreen(viewModel: ShoppingViewModel) {
    val items by viewModel.items.collectAsState()
    val total by viewModel.totalEstimated.collectAsState()

    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("MercadoFacil — Lista") })
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Item") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("R$") },
                    modifier = Modifier.width(120.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                val priceVal = price.toDoubleOrNull()
                if (name.isNotBlank()) {
                    viewModel.addItem(name = name.trim(), price = priceVal)
                    name = ""
                    price = ""
                }
            }) {
                Text("Adicionar")
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("Total estimado: R$ ${"%.2f".format(total)}", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn {
                items(items) { item ->
                    ShoppingItemRow(item = item, onDelete = { viewModel.deleteItem(item) })
                }
            }
        }
    }
}

@Composable
fun ShoppingItemRow(item: ShoppingItem, onDelete: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp), elevation = 2.dp) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, style = MaterialTheme.typography.subtitle1)
                Text(text = "Qtd: ${item.quantity} • R$ ${item.estimatedPrice ?: 0.0}", style = MaterialTheme.typography.body2)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
