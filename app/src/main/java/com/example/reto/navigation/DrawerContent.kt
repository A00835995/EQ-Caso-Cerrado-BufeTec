package com.example.reto.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.reto.R
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

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
        icon = { Icon(imageVector = Icons.Rounded.Info, contentDescription = "ChatBot") },
        label = { Text("ChatBot", fontSize = 17.sp) },
        selected = false,
        onClick = {
            scope.launch {
                navController.navigate("chatbot")
                drawerState.close()
            }
        }
    )
    // Navegación a los Procesos Legales
    NavigationDrawerItem(
        icon = { Icon(imageVector = Icons.Rounded.Info, contentDescription = "Gestión de Clientes") },
        label = { Text("Gestión de Clientes", fontSize = 17.sp) },
        selected = false,
        onClick = {
            scope.launch {
                navController.navigate("gestionClientes")
                drawerState.close()
            }
        }
    )

    // Navegación a la Gestión de Clientes
    NavigationDrawerItem(
            icon = { Icon(imageVector = Icons.Rounded.Info, contentDescription = "Procesos Legales") },
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
        icon = { Icon(imageVector = Icons.Rounded.Info, contentDescription = "Info de Abogados") },
        label = { Text("Info de Abogados", fontSize = 17.sp) },
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
        icon = { Icon(imageVector = Icons.Rounded.Info, contentDescription = "Foro") },
        label = { Text("Foro", fontSize = 17.sp) },
        selected = false,
        onClick = {
            scope.launch {
                navController.navigate("foro")
                drawerState.close()
            }
        }
    )


}
