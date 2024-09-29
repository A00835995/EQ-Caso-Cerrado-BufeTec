package com.example.reto.procesosLegales

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.Flow

// Este ViewModel interactúa con DataStore para manejar los favoritos
class ProcesosViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStoreManager = DataStoreManager(application)

    // Flujo que representa los favoritos almacenados en DataStore
    val favoriteItems: Flow<Set<String>> = dataStoreManager.favoriteItems

    // Función para agregar un favorito
    suspend fun addFavorite(title: String) {
        dataStoreManager.addFavorite(title)
    }

    // Función para eliminar un favorito
    suspend fun removeFavorite(title: String) {
        dataStoreManager.removeFavorite(title)
    }
}