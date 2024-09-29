package com.example.reto.forocomponentes

import androidx.compose.runtime.Composable

data class QuestionModel(
    val title: String? = "",
    val author: String? = "",
    val description: String? = "",
    val isFavorite: Boolean? = false,
    val isViewed: Boolean? = false,
    val response: String? = null,
    val responseAuthor: String? = null
)