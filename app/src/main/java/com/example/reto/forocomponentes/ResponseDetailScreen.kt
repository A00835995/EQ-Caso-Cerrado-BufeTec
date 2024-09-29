package com.example.reto.forocomponentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ResponseDetailScreen(
    question: QuestionModel,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.surface  // Utilizando el color de surface desde el tema
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Título: ${question.title}", style = MaterialTheme.typography.h6)
                Text(text = "Autor: ${question.author}", style = MaterialTheme.typography.body2)
                Text(text = "Descripción: ${question.description}", style = MaterialTheme.typography.body1)

                if (question.response != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Respuesta: ${question.response}", style = MaterialTheme.typography.body1)
                    Text(text = "Autor de la respuesta: ${question.responseAuthor}", style = MaterialTheme.typography.body2)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onDismiss() }) {
                    Text("Cerrar")
                }
            }
        }
    }
}
