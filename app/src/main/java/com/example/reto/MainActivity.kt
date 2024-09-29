package com.example.reto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.reto.ui.theme.RetoTheme
import com.example.reto.allChatBot.ChatScreen
import com.example.reto.navigation.AppNavHost
import com.google.firebase.auth.FirebaseAuth
//Emilio Cavazos A00835995

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(Modifier.padding(innerPadding))
                }
            }
        }
        FirebaseAuth.getInstance()
    }
}
