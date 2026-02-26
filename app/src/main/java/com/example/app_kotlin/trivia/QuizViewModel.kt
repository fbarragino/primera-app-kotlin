package com.example.app_kotlin.trivia

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(
            questions = seedQuestions()
        )
    )

    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onSelectedOption(index: Int) {
        val current = _uiState.value
        if (current.isFinished || current.showNextButton) return
        _uiState.value = current.copy(selectedIndex = index)
    }

    fun onConfirmAnswer() {
        val current = _uiState.value
        val selected = current.selectedIndex ?: return
        val currentQuestion = current.currentQuestion ?: return

        val isCorrect = selected == currentQuestion.correctIndex
        val feedbackText = if (isCorrect) "✅ Correcto" else "❌ Incorrecto"
        val newScore = if (isCorrect) current.score + 100 else current.score

        _uiState.value = current.copy(
            score = newScore,
            feedback = feedbackText,
            showNextButton = true
        )
    }

    fun onNextQuestion() {
        val current = _uiState.value
        val nextIndex = current.currentIndex + 1
        val isFinished = nextIndex >= current.questions.size

        _uiState.value = current.copy(
            currentIndex = nextIndex,
            selectedIndex = null,
            feedback = null,
            showNextButton = false,
            isFinished = isFinished
        )
    }

    private fun seedQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                title = "¿Qué palabra clave se usa para declarar una variable inmutable en Kotlin?",
                options = listOf("var", "val", "let", "const"),
                correctIndex = 1
            ),
            Question(
                id = 2,
                title = "En Jetpack Compose, ¿qué anotación marca una función como UI?",
                options = listOf("@UI", "@Widget", "@Composable", "@Compose"),
                correctIndex = 2
            ),
            Question(
                id = 3,
                title = "¿Qué componente se usa para listas eficientes y scrolleables?",
                options = listOf("Column", "RecyclerView", "Stack", "LazyColumn"),
                correctIndex = 3
            ),
            Question(
                id = 4,
                title = "La instrucción que permite restaurar estado tras recreación de Activity es",
                options = listOf("intentData", "savedInstanceState", "activityState", "bundleConfig"),
                correctIndex = 1
            ),
            Question(
                id = 5,
                title = "¿Cuál es el lenguaje oficial recomendado por Google para Android?",
                options = listOf("Java", "C++", "Kotlin", "Python"),
                correctIndex = 2
            ),
            Question(
                id = 6,
                title = "¿Qué componente de Jetpack se encarga de manejar el ciclo de vida?",
                options = listOf("ViewModel", "LiveData", "Lifecycle", "WorkManager"),
                correctIndex = 2
            )
        )
    }
}
