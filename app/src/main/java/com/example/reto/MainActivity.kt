package com.example.reto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reto.ui.theme.RetoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetoTheme {
                // Crear el controlador de navegación
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Establecer el NavHost con las pantallas
                    NavigationComponent(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "ProcesosLegalesScreen") {
        // Pantalla principal
        composable("ProcesosLegalesScreen") {
            ProcesosLegalesScreen(navController = navController)
        }
        // Pantalla de detalles
        composable(
            "detail/{itemTitle}",
            arguments = listOf(navArgument("itemTitle") { defaultValue = "" })
        ) { backStackEntry ->
            val itemTitle = backStackEntry.arguments?.getString("itemTitle") ?: ""
            DetailScreen(itemTitle = itemTitle, onBackClick = { navController.popBackStack() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetoTheme {
        val navController = rememberNavController()
        NavigationComponent(navController)
    }
}
