package com.example.reto.uicomponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextFieldDefaults

@Composable
fun SearchBar(searchText: TextFieldValue, onSearchTextChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { onSearchTextChange(it) },
        label = { Text("Buscar") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color(0xFF1E88E5), // Color del cursor
            focusedBorderColor = Color(0xFF1E88E5), // Color del borde cuando está enfocado
            unfocusedBorderColor = Color.Gray, // Color del borde cuando no está enfocado
            focusedLabelColor = Color(0xFF1E88E5), // Color del label cuando está enfocado
            unfocusedLabelColor = Color.Gray // Color del label cuando no está enfocado
        )
    )
}