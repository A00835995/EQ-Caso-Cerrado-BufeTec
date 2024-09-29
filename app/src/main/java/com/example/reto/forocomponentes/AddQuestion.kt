package com.example.reto.forocomponentes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddQuestionDialog(
    onDismiss: () -> Unit,
    onAddQuestion: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Agregar pregunta") },
        text = {
            Column {
                // Campo de texto para el título
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
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

                // Campo de texto para el autor
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Autor") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = Color(0xFF1E88E5),
                        focusedBorderColor = Color(0xFF1E88E5),
                        unfocusedBorderColor = Color.Gray,
                        focusedLabelColor = Color(0xFF1E88E5),
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de texto para la descripción
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
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
                    onAddQuestion(title, author, description)
                },
                colors = ButtonDefaults.textButtonColors(backgroundColor = Color(0xFF1E88E5))
            ) {
                Text("Agregar", color = Color.White)
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