package com.example.reto.procesosLegales


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun ProcesosLegalesScreen(navController: NavHostController, viewModel: ProcesosViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val favorites by viewModel.favoriteItems.collectAsState(initial = emptySet())

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    // Lista actualizada de procesos legales con diferentes nombres y explicaciones
    val originalCardItems = listOf(
        CardItem("Penales", "Proceso legal en donde se juzgan crímenes graves como homicidios o robos.", false),
        CardItem("Civiles", "Litigios entre personas o entidades privadas para resolver disputas civiles.", false),
        CardItem("Laborales", "Resolución de conflictos entre trabajadores y empleadores.", false),
        CardItem("Familiares", "Asuntos relacionados con el matrimonio, divorcio y custodia de hijos.", false),
        CardItem("Administrativos", "Resolución de disputas entre ciudadanos y organismos del gobierno.", false)
    )

    // Estado mutable que mantiene la lista actual de tarjetas
    var cardItems by remember { mutableStateOf(originalCardItems) }

    // Actualizar el estado de los favoritos
    LaunchedEffect(favorites) {
        cardItems = originalCardItems.map { item ->
            item.copy(isFavorite = favorites.contains(item.title))
        }.sortedByDescending { it.isFavorite } // Ordenar para que los favoritos vayan arriba
    }

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
                    .background(Color(0xFF2541B2)) // Color azul oscuro
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de búsqueda sin borde
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { newValue -> searchQuery = newValue },
                label = { Text("Buscar") },
                leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) }, // Ícono de búsqueda
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(24.dp)) // Bordes redondeados
                    .background(Color(0xFFEEF3FC)),  // Color de fondo
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color(0xFFEEF3FC), // Fondo de color claro
                    focusedBorderColor = Color.Transparent, // Sin borde al enfocar
                    unfocusedBorderColor = Color.Transparent // Sin borde al desenfocar
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Asegurar que LazyColumn permita scroll
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize() // Asegurarse de que ocupetodo el espacio disponible
                    .padding(horizontal = 16.dp)
            ) {
                items(filteredItems) { item ->
                    CardItemView(
                        cardItem = item,
                        onClick = {
                            // Navegar a la pantalla de detalles pasando el título
                            navController.navigate("detail/${item.title}")
                        },
                        onFavoriteClick = {
                            if (item.isFavorite) {
                                coroutineScope.launch { viewModel.removeFavorite(item.title) }
                            } else {
                                coroutineScope.launch { viewModel.addFavorite(item.title) }
                            }

                            // Reordenar la lista según los favoritos y no favoritos
                            cardItems = cardItems.map { originalItem ->
                                if (originalItem.title == item.title) {
                                    originalItem.copy(isFavorite = !item.isFavorite)
                                } else {
                                    originalItem
                                }
                            }.sortedByDescending { it.isFavorite } // Ordenar favoritos arriba
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun CardItemView(cardItem: CardItem, onClick: () -> Unit, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // Bordes redondeados para las tarjetas
            .clickable { onClick() },
        elevation = 4.dp,
        backgroundColor = Color(0xFFD9E3F0) // Color de fondo similar al de la imagen
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = cardItem.title, style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = cardItem.description, style = MaterialTheme.typography.body1)
            }
            IconButton(onClick = { onFavoriteClick() }) {
                if (cardItem.isFavorite) {
                    Icon(Icons.Filled.Star, contentDescription = "Favorito", tint = Color.Yellow)
                } else {
                    Icon(Icons.Outlined.StarOutline, contentDescription = "No Favorito")
                }
            }
        }
    }
}

// Data class para las tarjetas
data class CardItem(val title: String, val description: String, val isFavorite: Boolean)

@Preview(showBackground = true)
@Composable
fun ProcesosLegalesPreview() {
    ProcesosLegalesScreen(rememberNavController())
}