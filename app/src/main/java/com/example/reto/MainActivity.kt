package com.example.reto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.reto.uicomponents.ForumScreen
import com.example.reto.uicomponents.theme.AbogadosForoTheme
import com.google.firebase.auth.FirebaseAuthException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbogadosForoTheme {
                ForumScreen()  // Llama a la pantalla del foro
            }
        }
    }
}
