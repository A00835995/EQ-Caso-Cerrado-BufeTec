package com.example.reto.forocomponentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TabBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    val tabs = listOf("Nuevos", "Destacados", "Sin responder")

    // Definir colores para las pestañas seleccionadas y no seleccionadas
    val selectedColor = Color(0xFF1E88E5) // Azul para la pestaña seleccionada
    val unselectedColor = Color(0xFFBBDEFB) // Azul claro para las pestañas no seleccionadas

    TabRow(
        selectedTabIndex = tabs.indexOf(selectedTab),
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = selectedColor, // Color de fondo
        contentColor = Color.White // Color del texto en la pestaña seleccionada
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                text = {
                    Text(
                        text = tab,
                        color = if (selectedTab == tab) Color.White else unselectedColor // Cambiar color del texto
                    )
                }
            )
        }
    }
}