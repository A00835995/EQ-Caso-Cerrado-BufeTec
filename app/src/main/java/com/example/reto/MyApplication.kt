package com.example.reto

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Inicializa Firebase al inicio de la aplicación
        FirebaseApp.initializeApp(this)
    }
}