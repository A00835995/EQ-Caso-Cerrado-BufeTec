package com.example.reto.procesosLegales

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Crear una extensión para el DataStore
val Context.dataStore by preferencesDataStore("favorites_prefs")

class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val FAVORITES_KEY = stringSetPreferencesKey("favorites_key")
    }

    // Obtener los favoritos como Flow
    val favoriteItems: Flow<Set<String>> = dataStore.data.map { preferences ->
        preferences[FAVORITES_KEY] ?: emptySet()
    }

    // Guardar un favorito
    suspend fun addFavorite(title: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites + title
        }
    }

    // Eliminar un favorito
    suspend fun removeFavorite(title: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites - title
        }
    }
}