package com.example.reto

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.reto.allChatBot.ChatScreen
import org.junit.Rule
import org.junit.Test

class ChatBotInstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testChatScreenElementsExist() {
        val fakeNavController = FakeNavController(InstrumentationRegistry.getInstrumentation().targetContext)

        composeTestRule.setContent {
            ChatScreen(navController = fakeNavController)
        }

        // Verificar que el campo de texto y el botón existen
        composeTestRule.onNodeWithText("Mensaje al Asistente Inteligente").assertExists()
        composeTestRule.onNodeWithText("Send").assertExists()
    }

    @Test
    fun testUserMessageIsAdded() {
        val fakeNavController = FakeNavController(InstrumentationRegistry.getInstrumentation().targetContext)

        composeTestRule.setContent {
            ChatScreen(navController = fakeNavController)
        }

        // Simular entrada de texto en el campo de texto
        composeTestRule.onNodeWithText("Mensaje al Asistente Inteligente")
            .performTextInput("Hola")

        // Simular clic en el botón de "Send"
        composeTestRule.onNodeWithText("Send").performClick()

        // Esperar a que se procese el mensaje
        composeTestRule.waitForIdle()

        // Verificar que el mensaje "Hola" del usuario ha sido agregado a la lista
        composeTestRule.onNodeWithText("Hola").assertExists()
    }

    @Test
    fun testLoadingIndicatorDisappears() {
        val fakeNavController = FakeNavController(InstrumentationRegistry.getInstrumentation().targetContext)

        composeTestRule.setContent {
            ChatScreen(navController = fakeNavController)
        }

        // Simular entrada de texto en el campo de texto
        composeTestRule.onNodeWithText("Mensaje al Asistente Inteligente")
            .performTextInput("Hola")

        // Simular clic en el botón de "Send"
        composeTestRule.onNodeWithText("Send").performClick()

        // Esperar a que se procese el mensaje
        composeTestRule.waitForIdle()

        // Verificar que el indicador de carga no está visible después de recibir la respuesta
        composeTestRule.onNode(hasText("Loading...")).assertDoesNotExist()
    }




    // Clase simulada para NavController
    class FakeNavController(context: Context) : NavController(context) {
        override fun popBackStack(): Boolean {
            return true // Siempre devuelve true para fines de prueba
        }
    }
}
