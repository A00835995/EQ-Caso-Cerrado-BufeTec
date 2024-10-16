package com.example.reto.procesosLegales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(itemTitle: String, onBackClick: () -> Unit) {
    var tramitesList by remember { mutableStateOf(listOf<String>()) }
    val database = FirebaseDatabase.getInstance().reference.child("procesos_legales").child(itemTitle.lowercase()).child("tramites")

    // Leer los trámites de Firebase para el proceso legal seleccionado
    LaunchedEffect(itemTitle) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tramites = mutableListOf<String>()
                for (data in snapshot.children) {
                    val tramite = data.getValue(String::class.java)
                    tramite?.let { tramites.add(it) }
                }
                tramitesList = tramites
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja el error en caso de que falle la conexión a Firebase
            }
        })
    }

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
        // Usamos LazyColumn para que todo el contenido sea desplazable
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            item {
                Text(
                    text = "¿Cómo te podemos ayudar?",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF2541B2)
                )
            }
            items(tramitesList.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp), // Espaciado alrededor de cada card
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFBCC9E5))
                            .padding(16.dp) // Espaciado interno de la card
                    ) {
                        Text(
                            text = tramitesList[index],
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    DetailScreen(itemTitle = "Penales", onBackClick = {})
}