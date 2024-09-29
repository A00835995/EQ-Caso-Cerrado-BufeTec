package com.example.reto.forocomponentes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuestionList(questions: List<Pair<String, String>>) {
    Column {
        questions.forEach { question ->
            QuestionCard(question.first, question.second)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun QuestionCard(title: String, author: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Acción cuando se selecciona */ }
            .padding(8.dp)
            .background(Color(0xFFDDE7F2)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title)
            Text(text = "por $author")
        }
    }
}