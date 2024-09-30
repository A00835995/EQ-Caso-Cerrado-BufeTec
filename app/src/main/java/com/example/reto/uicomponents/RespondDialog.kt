package com.example.reto.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RespondDialog(
    question: QuestionModel,
    onDismiss: () -> Unit,
    onRespond: (String, String) -> Unit
) {
    var response by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Responder a la pregunta") },
        text = {
            Column {
                // Campo de texto para la respuesta
                OutlinedTextField(
                    value = response,
                    onValueChange = { response = it },
                    label = { Text("Respuesta") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color(0xFF1E88E5), // Color del cursor
                        focusedBorderColor = Color(0xFF1E88E5), // Color del borde cuando está enfocado
                        unfocusedBorderColor = Color.Gray, // Color del borde cuando no está enfocado
                        focusedLabelColor = Color(0xFF1E88E5), // Color del label cuando está enfocado
                        unfocusedLabelColor = Color.Gray // Color del label cuando no está enfocado
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de texto para el autor de la respuesta
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Autor de la respuesta") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color(0xFF1E88E5),
                        focusedBorderColor = Color(0xFF1E88E5),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFF1E88E5),
                        unfocusedLabelColor = Color.Gray
                    )
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onRespond(response, author)
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text("Responder", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text("Cancelar", color = Color.White)
            }
        }
    )
}