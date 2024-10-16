package com.example.reto

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.reto.ui.theme.MediumBlue
import com.example.reto.ui.theme.TecBlue


@Composable
fun AdministrarUsuarios(navController: NavController) {
    val usersRef = FirebaseDatabase.getInstance().getReference("users")
    val userList = remember { mutableStateListOf<User>() }
    var isDataLoaded by remember { mutableStateOf(false) }

    // Solo cargar los usuarios si aún no se han cargado
    if (!isDataLoaded) {
        usersRef.get().addOnSuccessListener { snapshot ->
            userList.clear() // Limpiar la lista para evitar duplicados
            for (userSnapshot in snapshot.children) {
                val user = userSnapshot.getValue(User::class.java)
                user?.let { userList.add(it) }
            }
            isDataLoaded = true // Marcar como cargado
        }
    }

    // Ordenar usuarios: primero los que tienen relación "cliente" y "practicante"
    val sortedUserList = userList.sortedWith(compareByDescending { user ->
        when (user.relacion) {
            "Colaborador", "Practicante" -> 1
            else -> 0
        }
    })

    // Mostrar lista de usuarios
    Column() {
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = "Administrador de Usuarios",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            color = MediumBlue
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sortedUserList) { user ->
                UserItem(user) { newEstado ->
                    // Actualizar el estado del usuario en Firebase
                    user.userId?.let { usersRef.child(it).child("estado").setValue(newEstado) }
                }
            }
        }
    }
}


@Composable
fun UserItem(user: User, onEstadoChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var currentEstado by remember { mutableStateOf(user.estado) } // Estado local para actualizar dinámicamente

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(5.dp)
            .background(Color(0xFFBCC9E5))
    ) {
        Text(text = "Nombre: ${user.nombre}", modifier = Modifier.padding(5.dp),fontSize = 18.sp)
        Text(text = "Correo: ${user.correo}", modifier = Modifier.padding(5.dp),fontSize = 18.sp)
        Text(text = "Relación: ${user.relacion}", modifier = Modifier.padding(5.dp),fontSize = 18.sp)
        Text(text = "Estado: $currentEstado", modifier = Modifier.padding(5.dp),fontSize = 18.sp)

        if (expanded) {
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                if (currentEstado == "Activo") {
                    Button(onClick = {
                        onEstadoChange("Inactivo")
                        currentEstado = "Inactivo" // Actualizar el estado local para ocultar el botón
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = TecBlue),
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(text = "Desactivar", color = Color.White)
                    }
                } else {
                    Button(onClick = {
                        onEstadoChange("Activo")
                        currentEstado = "Activo" // Actualizar el estado local para ocultar el botón
                    },
                        colors = ButtonDefaults.buttonColors(containerColor = TecBlue),
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(text = "Activar", color = Color.White)
                    }
                }
            }

        }
    }
}
