package com.example.reto.forocomponentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.Color

@Composable
fun FullDescriptionScreen(question: QuestionModel, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Detalle de la Pregunta",
                        color = Color(0xFF1E88E5) // Color azul
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                backgroundColor = MaterialTheme.colors.surface // Mantener el fondo neutro o ajustarlo
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)) {
                Text(text = "Título: ${question.title}", style = MaterialTheme.typography.h6)
                Text(text = "Autor: ${question.author}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Descripción: ${question.description}", style = MaterialTheme.typography.body1)

                Spacer(modifier = Modifier.height(16.dp))

                if (!question.response.isNullOrEmpty()) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(text = "Respuesta: ${question.response}", style = MaterialTheme.typography.body1)
                    Text(text = "Autor de la respuesta: ${question.responseAuthor}", style = MaterialTheme.typography.body2)
                }
            }
        }
    )
}