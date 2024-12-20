package com.example.reto

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reto.ui.theme.TecBlue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(navController: NavController) {
    var correoInput by remember { mutableStateOf("") }
    var contraInput by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    val textFieldBackgroundColor = Color(0xFFBCC9E5) // Color de fondo para el TextField
    val textFieldBorderColor = Color(0xFF1768AC) // Color del borde


    fun iniciarSesion() {
        auth.signInWithEmailAndPassword(correoInput, contraInput)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Obtener el usuario autenticado
                    val user = auth.currentUser
                    val userId = user?.uid
                    Log.d("FirebaseDatabase", "ID del usuario autenticado: $userId")
                    // Recuperar la información del usuario desde Firebase Realtime Database
                    userId?.let {
                        usersRef.child(it).get().addOnSuccessListener { dataSnapshot ->
                            val userInfo = dataSnapshot.getValue(User::class.java)
                            Log.d("FirebaseDatabase", "Datos del usuario autenticado: $userInfo")

                            if (userInfo?.estado == "Inactivo") {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Tu cuenta está inactiva. Contacta a un administrador.")
                                }
                            } else {
                                // Redirigir a la pantalla principal si el usuario está activo
                                navController.navigate("mainpage")
                            }
                        }.addOnFailureListener { exception ->
                            Log.e("FirebaseDatabase", "Error al obtener los datos del usuario", exception)
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Error al obtener la información del usuario")
                            }
                        }
                    }
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
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.buffeeee),
            contentDescription = "Logo",
            modifier = Modifier
                .background(Color(0xFF2541B2))
                .fillMaxWidth()
                .padding(10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Texto de login con mayor tamaño
        Text(
            text = "Login",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF2541B2),
        )
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
            visualTransformation = PasswordVisualTransformation(),
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
                containerColor = Color(0xFF2541B2)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }

        Button(
            onClick = {
                auth.signOut()//sale del usuairo anterior
                // usuario creado como invitado
                val guestUserId = "t1Uobs8YaaOvFY47cvXJBETrPPH3"
                usersRef.child(guestUserId).get().addOnSuccessListener { dataSnapshot ->
                    val guestUserInfo = dataSnapshot.getValue(User::class.java)
                    Log.d("FirebaseDatabase", "Datos del usuario invitado: $guestUserInfo")

                    // Aquí puedes manejar los datos del usuario invitado como necesites
                    navController.navigate("mainpage")
                }.addOnFailureListener { exception ->
                    Log.e("FirebaseDatabase", "Error al obtener los datos del usuario invitado", exception)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Error al obtener la información del usuario invitado")
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xE92541B2)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Entrar como invitado")
        }

        // Texto clicable para registrar una nueva cuenta
        val annotatedText = buildAnnotatedString {
            append("¿No tienes cuenta? ")
            withStyle(style = SpanStyle(color = Color(0xFF03256C))) {
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
