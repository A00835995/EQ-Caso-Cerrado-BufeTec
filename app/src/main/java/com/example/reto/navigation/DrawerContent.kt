package com.example.reto.navigation

import UserViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BookOnline
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.ChatBubbleOutline
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.School
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.reto.R
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DrawerContent(navController: NavHostController, userViewModel: UserViewModel = viewModel(), drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    // Llamar a fetchUserData() solo una vez cuando el Composable se carga
    LaunchedEffect(Unit) {
        userViewModel.fetchUserData()
    }

    // Obtenemos el rol del usuario desde el ViewModel
    val userRelation = userViewModel.userRelation.collectAsState().value

    Image(
        painter = painterResource(id = R.drawable.buffeeee),
        contentDescription = "Logo",
        modifier = Modifier
            .background(Color(0xFF2541B2))
            .fillMaxWidth()
    )
    HorizontalDivider()

    Spacer(modifier = Modifier.height(4.dp))

    NavigationDrawerItem(
        icon = { Icon(imageVector = Icons.Rounded.Home, contentDescription = "Página Principal") },
        label = { Text("Página Principal", fontSize = 17.sp) },
        selected = false,
        onClick = {
            scope.launch {
                navController.navigate("mainpage")
                drawerState.close()
            }
        }
    )

    Spacer(modifier = Modifier.height(4.dp))

    // Navegación al ChatBot
    NavigationDrawerItem(
        icon = { Icon(imageVector = Icons.Rounded.ChatBubble, contentDescription = "ChatBot") },
        label = { Text("ChatBot", fontSize = 17.sp) },
        selected = false,
        onClick = {
            scope.launch {
                navController.navigate("chatbot")
                drawerState.close()
            }
        }
    )

    if (userRelation == "Practicante" || userRelation == "Colaborador") {
        // Navegación a los Gestión de Clientes
        NavigationDrawerItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Gestión de Clientes"
                )
            },
            label = { Text("Gestión de Clientes", fontSize = 17.sp) },
            selected = false,
            onClick = {
                scope.launch {
                    navController.navigate("gestionClientes")
                    drawerState.close()
                }
            }
        )
    }

    // Navegación a la Procesos Legales
    NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Rounded.BookOnline, contentDescription = "Procesos Legales") },
    label = { Text("Procesos Legales", fontSize = 17.sp) },
    selected = false,
    onClick = {
        scope.launch {
            navController.navigate("ProcesosLegalesScreen")
            drawerState.close()
        }
    }
    )

    // Navegación a la Info de Abogados
    NavigationDrawerItem(
        icon = { Icon(imageVector = Icons.Rounded.School, contentDescription = "Info de Abogados") },
        label = { Text("Nuestros Abogados", fontSize = 17.sp) },
        selected = false,
        onClick = {
            scope.launch {
                navController.navigate("infoAbogados")
                drawerState.close()
            }
        }
    )

    // Navegación al Foro
    NavigationDrawerItem(
        icon = { Icon(imageVector = Icons.Rounded.Chat, contentDescription = "Foro") },
        label = { Text("Foro", fontSize = 17.sp) },
        selected = false,
        onClick = {
            scope.launch {
                navController.navigate("foro")
                drawerState.close()
            }
        }
    )

    // Navegación al Administrador
    if(userRelation == "Colaborador")
    {
        NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Rounded.Person, contentDescription = "Administrador") },
            label = { Text("Administrar", fontSize = 17.sp) },
            selected = false,
            onClick = {
                scope.launch {
                    navController.navigate("adminUsuarios")
                    drawerState.close()
                }
            }
        )
    }
}
