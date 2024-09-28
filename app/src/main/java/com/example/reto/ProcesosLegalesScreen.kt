package com.example.reto

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.reto.ui.theme.RetoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcesosLegalesScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Lista original de tarjetas
    val cardItems = listOf(
        CardItem("Penales", "Soy estudiante de leyes y necesito ayuda para resolver una tarea..."),
        CardItem("Civiles", "Soy estudiante de leyes y necesito ayuda para resolver un problema..."),
        CardItem("Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
        CardItem("Lorem Ipsum", "Lorem ipsum dolor sit amet, consectetur adipiscing elit."),
        CardItem("Penales", "Otro caso relacionado con leyes penales...")
    )

    // Filtrar las tarjetas según el texto ingresado en la búsqueda
    val filteredItems = cardItems.filter {
        it.title.contains(searchQuery.text, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Procesos Legales", color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2541B2)), // Color azul oscuro
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2541B2) // Fondo azul oscuro
                )
            )
        }
    ) { paddingValues ->
        // El contenido de la lista de tarjetas
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newValue -> searchQuery = newValue },
                label = { Text("Buscar") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.LightGray),  // Aplica el color de fondo
                singleLine = true, // Mantiene el TextField en una sola línea
                colors = TextFieldDefaults.outlinedTextFieldColors(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar las tarjetas filtradas
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(filteredItems.size) { index ->
                    CardItemView(
                        cardItem = filteredItems[index],
                        onClick = {
                            // Navegar a la pantalla de detalles pasando el título
                            navController.navigate("detail/${filteredItems[index].title}")
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun CardItemView(cardItem: CardItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = cardItem.title, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = cardItem.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Data class para las tarjetas
data class CardItem(val title: String, val description: String)

@Preview(showBackground = true)
@Composable
fun ProcesosLegalesPreview() {
    RetoTheme {
        ProcesosLegalesScreen(rememberNavController())
    }
}
