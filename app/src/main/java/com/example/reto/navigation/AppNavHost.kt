package com.example.reto.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reto.allChatBot.ChatScreen
import com.example.reto.LogIn
import com.example.reto.SignUp
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Navegación siempre comenzando en la pantalla de Login
    NavHost(
        navController = navController,
        startDestination = "login"  // Siempre empieza en Login
    ) {
        // Pantalla Login
        composable("login") {
            LogIn(navController)
        }
        // Pantalla SignUp
        composable("signup") {
            SignUp(navController)
        }
        // Pantalla ChatBot
        composable("chatbot") {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                ChatScreen(modifier = Modifier.padding(innerPadding), navController)
            }
        }
    }
}

//esto es para hacer  un scaffold
/*
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        // Aquí puedes agregar un drawer si es necesario
                        Text(text = "Menú lateral")
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("ChatBot") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch { drawerState.open() }
                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    },
                    content = {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            ChatScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                    }
                )
            }



 */