package com.example.reto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(itemTitle: String, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            // Barra superior azul con el título dinámico
            TopAppBar(
                title = { Text(text = itemTitle, color = Color.White, fontSize = 20.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2541B2)),
                navigationIcon = {
                    // Botón de retroceso
                    IconButton(onClick = { onBackClick() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2541B2) // Azul oscuro
                )
            )
        }
    ) { paddingValues ->
        // Cuadro de texto gris en el centro con texto aleatorio
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth(0.9f)
                    .height(400.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Texto aleatorio por mientras...",
                    modifier = Modifier.fillMaxSize(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen(itemTitle = "Penales", onBackClick = {})
}
