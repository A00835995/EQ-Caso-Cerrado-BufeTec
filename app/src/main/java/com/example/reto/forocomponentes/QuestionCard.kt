package com.example.reto.forocomponentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuestionCard(
    title: String,
    author: String,
    description: String,
    isFavorite: Boolean,
    isViewed: Boolean,
    hasResponse: Boolean,
    onFavoriteToggle: () -> Unit,
    onClick: () -> Unit,
    onRespondClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.h6)
            Text(text = "por $author", style = MaterialTheme.typography.body2)
            Text(text = description, style = MaterialTheme.typography.body1)

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                // Icono de favorito (estrella)
                IconButton(onClick = onFavoriteToggle) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                        tint = if (isFavorite) Color(0xFF1E88E5) else Color.Gray // Azul si está marcado, gris si no
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (!hasResponse) {
                    // Botón de responder
                    TextButton(onClick = onRespondClick) {
                        Text("Responder", color = Color(0xFF1E88E5)) // Cambia el color a azul
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Respondido",
                        tint = Color.Green // Cambio de color a verde
                    )
                }
            }
        }
    }
}
