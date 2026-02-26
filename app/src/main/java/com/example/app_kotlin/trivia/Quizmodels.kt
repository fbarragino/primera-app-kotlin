package com.example.app_kotlin.trivia

data class Question(
    val id: Int,
    val title: String,
    val options: List<String>,
    val correctIndex: Int
)

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val feedback: String? = null, // "✅ Correcto" o "❌ Incorrecto"
    val showNextButton: Boolean = false // Para mostrar "Siguiente" tras el feedback
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)
    
    val isLastQuestion: Boolean
        get() = currentIndex == questions.size - 1
}
