package com.example.reto.allChatBot

import android.util.Log
import com.example.reto.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

data class Message(val text: String, val isUser: Boolean)

fun getResponse(userMessage: String, callback: (String) -> Unit) {
    val client = OkHttpClient()
    val apiKey = BuildConfig.MY_API
    val url = "https://api.openai.com/v1/chat/completions"

    val requestBody = """
        {
            "model": "gpt-3.5-turbo",
            "messages": [
                {"role": "system", "content": "Eres un asistente virtual para Bufetec, una plataforma que asiste a los clientes con dudas sobre procedimientos legales. Tu objetivo es proporcionar respuestas claras y útiles sobre cuestiones legales. Si el usuario tiene una duda más específica o compleja que no puedas resolver, indícale que puede ir al apartado de nuestros abogados donde podria ver los abogados que contamos y sus especialidades. Las areas legales que abordamos son la civil, familiar y mercantil si quieres conocer mas sobre estas areas ve a nuestra pagina de Procesos Legales"},
                {"role": "user", "content": "$userMessage"}
            ]
        }
    """.trimIndent()

    val request = Request.Builder()
        .url(url)
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Bearer $apiKey")
        .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("error", "API failed", e)
            callback("No se pudo obtener una respuesta. Intenta de nuevo.")
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                Log.e("error", "Código de error inesperado $response")
                callback("Error: ${response.code}")
                return
            }

            val body = response.body?.string()
            if (body != null) {
                try {
                    val jsonObject = JSONObject(body)
                    val jsonArray: JSONArray = jsonObject.getJSONArray("choices")
                    val textResult = jsonArray.getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    callback(textResult.trim())
                } catch (e: Exception) {
                    Log.e("error", "Error al parsear JSON", e)
                    callback("Error al procesar la respuesta. Por favor, intenta de nuevo.")
                }
            } else {
                callback("No se recibió respuesta del bot.")
            }
        }
    })
}
