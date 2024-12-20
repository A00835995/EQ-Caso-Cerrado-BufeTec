package com.example.reto.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reto.AdministrarUsuarios
import com.example.reto.allChatBot.ChatScreen
import com.example.reto.LogIn
import com.example.reto.SignUp
import com.example.reto.mainPage.MainPageContent
import com.example.reto.procesosLegales.ProcesosLegalesScreen
import com.example.reto.procesosLegales.DetailScreen
import com.example.reto.infoygestion.GestionClientesScreen
import com.example.reto.infoygestion.InfoAbogadosScreen
import com.example.reto.forocomponentes.ForumScreen
import com.example.reto.mainPage.PerfilScreen

import kotlinx.coroutines.launch
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost(modifier: Modifier) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // Condicionamos el uso del gesto para abrir el Drawer y que no funcione cuando esta en el sign up ni log in
    val gesturesEnabled = currentRoute != "login" && currentRoute != "signup"

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(navController = navController, drawerState = drawerState)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.fillMaxSize()
        ) {
            // Pantalla Login sin Drawer
            composable("login") {
                LogIn(navController)
            }
            // Pantalla SignUp sin Drawer
            composable("signup") {
                SignUp(navController)
            }
            // Pantallas con TopBar
            composable("mainpage") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) { innerPadding ->
                    MainPageContent(navController, modifier = Modifier.padding(innerPadding))
                }
            }
            // Pantalla ChatBot (con TopBar)
            composable("chatbot") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    ChatScreen(modifier = Modifier.padding(paddingValues), navController)
                }
            }
            composable("ProcesosLegalesScreen") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) {
                    ProcesosLegalesScreen(navController)
                }
            }
            composable(
                "detail/{itemTitle}",
                arguments = listOf(navArgument("itemTitle") { defaultValue = "" })
            ) { backStackEntry ->
                val itemTitle = backStackEntry.arguments?.getString("itemTitle") ?: ""
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) {
                    DetailScreen(itemTitle = itemTitle, onBackClick = { navController.popBackStack() }, navController)
                }
            }
            composable("gestionClientes") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) {
                    GestionClientesScreen(navController)
                }
            }
            composable("infoAbogados") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) {
                    InfoAbogadosScreen(navController)
                }
            }
            composable("foro") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) {
                    ForumScreen(navController)
                }
            }
            composable("adminUsuarios") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) {
                    AdministrarUsuarios(navController)
                }
            }
            composable("perfil") {
                Scaffold(
                    topBar = {
                        TopBar(
                            onOpenDrawer = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            },
                            navController
                        )
                    }
                ) {
                    PerfilScreen(navController)
                }
            }
        }
    }
}