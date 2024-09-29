package com.example.reto.allChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable
fun ChatB(message: Message) {
    val alignment = if (message.isUser) Arrangement.End else Arrangement.Start
    val backgroundBrush = if (message.isUser) {
        Brush.horizontalGradient(listOf(Color(0xFF06BDE0), Color(0xFF06BDE0)))
    } else {
        SolidColor(Color(0xFF33354B))
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        horizontalArrangement = alignment
    ) {
        Box(
            modifier = Modifier
                .background(brush = backgroundBrush, shape = RoundedCornerShape(16.dp))
                .padding(12.dp)
                .widthIn(max = 300.dp)
        ) {
            Text(text = message.text, color = Color.White)
        }
    }
}
