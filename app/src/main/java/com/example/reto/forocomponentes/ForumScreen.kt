package com.example.reto.forocomponentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavHostController

@Composable
fun ForumScreen(navHostController: NavHostController) {
    val questions = remember { mutableStateListOf<QuestionModel>() }
    var searchText by remember { mutableStateOf(TextFieldValue("")) } // Estado para el campo de búsqueda
    var showDialog by remember { mutableStateOf(false) }
    var showResponseDialog by remember { mutableStateOf(false) }
    var selectedQuestion: QuestionModel? by remember { mutableStateOf(null) }
    var showDetail by remember { mutableStateOf(false) } // Estado para mostrar el detalle de la pregunta

    // Referencia a Firebase
    val database = FirebaseDatabase.getInstance()
    val questionsRef = database.getReference("questions")

    // Escucha los cambios en Firebase y actualiza la lista de preguntas
    LaunchedEffect(Unit) {
        questionsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newQuestions = mutableListOf<QuestionModel>()
                for (questionSnapshot in snapshot.children) {
                    val question = questionSnapshot.getValue(QuestionModel::class.java)
                    if (question != null) {
                        newQuestions.add(question)
                    }
                }
                questions.clear()
                questions.addAll(newQuestions)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // Ordenar preguntas: destacadas primero, luego sin respuesta, luego respondidas
    val filteredQuestions = questions.sortedWith(
        compareByDescending<QuestionModel> { it.isFavorite ?: false }
            .thenBy { it.response != null }
    ).filter {
        it.title?.contains(searchText.text, ignoreCase = true) == true ||
                it.description?.contains(searchText.text, ignoreCase = true) == true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Foro Interactivo", color = Color.White) },
                backgroundColor = Color(0xFF1E88E5) // Azul para la barra superior
            )
        },
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize().padding(padding)) {
                if (showDetail && selectedQuestion != null) {
                    // Muestra la pantalla de detalle
                    FullDescriptionScreen(
                        question = selectedQuestion!!,
                        onBack = { showDetail = false } // Para cerrar la pantalla de detalle
                    )
                } else {
                    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

                        // Barra de búsqueda
                        SearchBar(searchText) { newText ->
                            searchText = newText
                        }

                        // Listado de preguntas filtradas
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            items(filteredQuestions) { question ->
                                QuestionCard(
                                    title = question.title ?: "", // Evitar valores nulos
                                    author = question.author ?: "",
                                    description = question.description ?: "",
                                    isFavorite = question.isFavorite ?: false,
                                    isViewed = question.isViewed ?: false,
                                    hasResponse = question.response != null,
                                    onFavoriteToggle = {
                                        val newFavoriteStatus = !(question.isFavorite ?: false)
                                        val updatedQuestion = question.copy(isFavorite = newFavoriteStatus)
                                        questionsRef.child(question.title!!).setValue(updatedQuestion)

                                        // Reorganiza las preguntas inmediatamente
                                        questions.remove(question)
                                        questions.add(0, updatedQuestion) // Coloca la pregunta en la primera posición
                                    },
                                    onClick = {
                                        selectedQuestion = question
                                        showDetail = true // Muestra el detalle cuando se hace clic en la tarjeta
                                    },
                                    onRespondClick = {
                                        selectedQuestion = question
                                        showResponseDialog = true
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                )
                            }
                        }
                    }

                    // Botón flotante para agregar preguntas
                    FloatingActionButton(
                        onClick = { showDialog = true },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        backgroundColor = Color(0xFF1E88E5) // Azul para el botón flotante
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Agregar pregunta",
                            tint = Color.White // Cambia el color del símbolo + a blanco
                        )
                    }

                    // Diálogo para agregar una nueva pregunta
                    if (showDialog) {
                        AddQuestionDialog(
                            onDismiss = { showDialog = false },
                            onAddQuestion = { title, author, description ->
                                val newQuestion = QuestionModel(
                                    title = title,
                                    author = author,
                                    description = description
                                )
                                questionsRef.child(title).setValue(newQuestion)
                                showDialog = false
                            }
                        )
                    }

                    // Diálogo para responder una pregunta
                    if (showResponseDialog && selectedQuestion != null) {
                        RespondDialog(
                            question = selectedQuestion!!,
                            onDismiss = { showResponseDialog = false },
                            onRespond = { responseText, author ->
                                val updatedQuestion = selectedQuestion!!.copy(
                                    response = responseText,
                                    responseAuthor = author
                                )
                                questionsRef.child(updatedQuestion.title!!).setValue(updatedQuestion)
                                showResponseDialog = false
                            }
                        )
                    }
                }
            }
        }
    )
}