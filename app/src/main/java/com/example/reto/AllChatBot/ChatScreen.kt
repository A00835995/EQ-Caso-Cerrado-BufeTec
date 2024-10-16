package com.example.reto.allChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reto.allChatBot.ChatB
import com.example.reto.allChatBot.getResponse
import com.example.reto.ui.theme.TecBlue

@Composable
fun ChatScreen(modifier: Modifier = Modifier,navController: NavController) {
    var userInput by remember { mutableStateOf("") }
    val messageList = remember { mutableStateListOf<Message>() }
    var isLoading by remember { mutableStateOf(false) } // Estado de carga

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()  // Ocupa todo el ancho
                .background(color = Color(0xFF2541B1))
        ) {
            Text(text = "Chat Ai", color = Color.White, modifier = Modifier.padding(16.dp)) // Texto centrado
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(messageList) { message ->
                    ChatB(message)
                }

                // Agregar un ítem extra cuando isLoading es true
                if (isLoading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.Start)
                                .testTag("LoadingIndicator")
                        )
                    }
                }
            }


            // Input and Send button
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                label = { Text("Mensaje al Asistente Inteligente") },
                // Configura colores...
                modifier = Modifier.fillMaxWidth()
                    .testTag("UserInputField")
            )

            Button(
                onClick = {
                    if (userInput.isNotEmpty()) {
                        messageList.add(Message(userInput, isUser = true))
                        isLoading = true
                        getResponse(userInput) { response ->
                            messageList.add(Message(response, isUser = false))
                            isLoading = false
                        }
                        userInput = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = TecBlue),
                modifier = Modifier.fillMaxWidth()
                    .testTag("SendButton")
            ) {
                Text(text = "Send")
            }

        }
    }
}
