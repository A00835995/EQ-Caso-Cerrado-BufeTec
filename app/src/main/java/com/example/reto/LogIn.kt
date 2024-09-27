package com.example.reto

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LogIn() {
    var correoInput by remember { mutableStateOf("") }
    var contraInput by remember { mutableStateOf("") }

    val textFieldBackgroundColor = Color(0xFFE0F7FA) // Color de fondo para el TextField
    val textFieldBorderColor = Color(0xFF54DBEE) // Color del borde

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Login")
        OutlinedTextField(
            value = correoInput,
            onValueChange = { correoInput = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = textFieldBackgroundColor,
                unfocusedContainerColor = textFieldBackgroundColor,
                focusedIndicatorColor = textFieldBorderColor,
                unfocusedIndicatorColor = textFieldBorderColor
            ),
            shape = RoundedCornerShape(16.dp)
        )
        OutlinedTextField(
            value = contraInput,
            onValueChange = { contraInput = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = textFieldBackgroundColor,
                unfocusedContainerColor = textFieldBackgroundColor,
                focusedIndicatorColor = textFieldBorderColor,
                unfocusedIndicatorColor = textFieldBorderColor
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Button(
            onClick = { /*TODO: Implementar lógica de login*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF06BDE0)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = { /*TODO: Implementar lógica de invitado*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF54DBEE)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Entrar como invitado")
        }

        // Texto clicable para registrar una nueva cuenta
        val annotatedText = buildAnnotatedString {
            append("¿No tienes cuenta? ")
            withStyle(style = SpanStyle(color = Color(0xFF06BDE0))) {
                pushStringAnnotation(tag = "URL", annotation = "https://www.google.com")
                append("Sign In")
                pop()
            }
        }
        ClickableText(
            text = annotatedText,
            onClick = { /* Agregar lógica de navegación aquí */ },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
