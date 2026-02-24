package com.example.app_kotlin.trivia

data class Question(
    val id: Int,
    val title: String,          // Pregunta super dificil
    val options: List<String>, // [Respuesta 1, Respuesta 2, Respuesta 3, Respuesta 4]
    val correctIndex: Int       // 2
)

data class QuizUiState(
    val questions: List<Question> = emptyList(), // Banco de preguntas
    val currentIndex: Int = 0,                   // Pregunta actual
    val selectedIndex: Int? = null,              // Opcion seleccionada (null si no hay)
    val score: Int = 0,                          // Puntaje
    val isFinished: Boolean = false              // Flag para pantalla final
)