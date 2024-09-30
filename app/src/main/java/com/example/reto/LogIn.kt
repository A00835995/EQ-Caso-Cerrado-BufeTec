package com.example.reto

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(navController: NavController) {
    var correoInput by remember { mutableStateOf("") }
    var contraInput by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()

    val textFieldBackgroundColor = Color(0xFFE0F7FA) // Color de fondo para el TextField
    val textFieldBorderColor = Color(0xFF54DBEE) // Color del borde


    fun iniciarSesion() {
        auth.signInWithEmailAndPassword(correoInput, contraInput)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("mainpage")
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("No se reconoce este correo y contraseña")
                    }
                }
            }
    }

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
            onClick = { iniciarSesion()  },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF06BDE0)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = {  navController.navigate("mainpage") },
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
                append("Sign Up")
                pop()
            }
        }
        ClickableText(
            text = annotatedText,
            onClick = { navController.navigate("signup") },
            modifier = Modifier.fillMaxWidth()
        )

        SnackbarHost(hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
        ) { data ->
            Box(
                modifier = Modifier.fillMaxWidth() // Asegura que el Box ocupe todo el ancho
            ) {
                Snackbar(
                    snackbarData = data,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.Center),
                    containerColor = Color(0xFF06BDE0), // Color del fondo del Snackbar
                    contentColor = Color.Black // Color del texto del mensaje
                )
            }
        }

    }
}
