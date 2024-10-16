package com.example.reto

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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    var correoInput by remember { mutableStateOf("") }
    var nombreInput by remember { mutableStateOf("") }
    var contraInput by remember { mutableStateOf("") }
    var contra2Input by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // Estado para controlar el desplegable
    val options = listOf("Colaborador", "Practicante", "Cliente")
    var selectedOption by remember { mutableStateOf("") } // Variable para mostrar la opción seleccionada
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    val textFieldBackgroundColor = Color(0xFFBCC9E5) // Color de fondo para el TextField
    val textFieldBorderColor = Color(0xFF1768AC) // Color del borde

    fun crearCuenta() {
        if (contraInput == contra2Input) {
            if (contraInput.length < 6) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("La contraseña debe de ser de mínimo 6 caracteres")
                }
            } else if (!correoInput.endsWith("@gmail.com")) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("El correo debe ingresarse correctamente")
                }
            }else if(selectedOption.isEmpty()){
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Debe seleccionar una relación con la aplicación")
                }
            } else {
                auth.createUserWithEmailAndPassword(correoInput, contraInput)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Obtener el ID del usuario justo después de que se haya creado la cuenta
                            val userId = auth.currentUser?.uid
                            // Crear un objeto con los datos del usuario

                            val estado = if (selectedOption == "Cliente") "Activo" else "Inactivo"

                            val user = User(
                                userId = userId,
                                nombre = nombreInput,
                                correo = correoInput,
                                relacion = selectedOption,
                                estado = estado
                            )

                            // Guardar la información del usuario en Firebase Realtime Database
                            userId?.let {
                                usersRef.child(it).setValue(user)
                                    .addOnSuccessListener {
                                        // Redirigir al login si la información se guardó exitosamente
                                        navController.navigate("login")
                                    }
                                    .addOnFailureListener {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar("Error al guardar la información del usuario")
                                        }
                                    }
                            }
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Error al crear la cuenta")
                            }
                        }
                    }
            }
        } else {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Las contraseñas no coinciden")
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
        // Texto de Sign Up con mayor tamaño
        Text(
            text = "Sign Up",
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
            value = nombreInput,
            onValueChange = { nombreInput = it },
            label = { Text("Nombre Completo") },
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

        // Menú desplegable
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded } // Cambia el estado al hacer clic
        ) {
            OutlinedTextField(
                value = selectedOption.ifEmpty { "Seleccionar relación" },
                onValueChange = {},
                readOnly = true, // Campo de solo lectura
                label = { Text("Relación con nosotros") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor() // Esto asegura que el menú esté anclado correctamente
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldBackgroundColor,
                    unfocusedContainerColor = textFieldBackgroundColor,
                    focusedIndicatorColor = textFieldBorderColor,
                    unfocusedIndicatorColor = textFieldBorderColor
                ),
                shape = RoundedCornerShape(16.dp)
            )

            // Opciones del menú desplegable
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false // Cierra el menú al seleccionar
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = contra2Input,
            onValueChange = { contra2Input = it },
            label = { Text("Verificar Contraseña") },
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
            onClick = {  crearCuenta() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2541B2)
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Registrarme")
        }

        // Clickable text link
        val annotatedText = buildAnnotatedString {
            append("Ya tienes una cuenta? ")
            withStyle(style = SpanStyle(color = Color(0xFF03256C))) {
                pushStringAnnotation(tag = "URL", annotation = "https://www.google.com")
                append("Login")
                pop()
            }
        }
        ClickableText(
            text = annotatedText,
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge
        )

    }
}
