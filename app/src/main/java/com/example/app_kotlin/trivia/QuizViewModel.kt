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
        val newLives = if (isCorrect) current.lives else current.lives - 1

        _uiState.value = current.copy(
            score = newScore,
            lives = newLives,
            feedback = feedbackText,
            showNextButton = true
        )
    }

    fun onNextQuestion() {
        val current = _uiState.value
        
        if (current.lives <= 0) {
            _uiState.value = current.copy(
                isFinished = true,
                reasonForFinish = FinishReason.NO_LIVES
            )
            return
        }

        val nextIndex = current.currentIndex + 1
        val isFinished = nextIndex >= current.questions.size

        _uiState.value = current.copy(
            currentIndex = nextIndex,
            selectedIndex = null,
            feedback = null,
            showNextButton = false,
            isFinished = isFinished,
            reasonForFinish = if (isFinished) FinishReason.COMPLETED else current.reasonForFinish
        )
    }

    fun restartQuiz() {
        _uiState.value = QuizUiState(questions = seedQuestions())
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
            ),
            Question(
                id = 7,
                title = "¿Cómo se llama la función que permite añadir funcionalidad a una clase existente sin heredar de ella?",
                options = listOf("Función anónima", "Función de extensión", "Función Lambda", "Función Inline"),
                correctIndex = 1
            ),
            Question(
                id = 8,
                title = "¿Qué palabra clave se utiliza para pausar la ejecución de una corrutina?",
                options = listOf("pause", "wait", "stop", "suspend"),
                correctIndex = 3
            ),
            Question(
                id = 9,
                title = "¿Cuál es la principal ventaja de una 'Data Class' en Kotlin?",
                options = listOf("No permite nulos", "Solo guarda enteros", "Genera automáticamente equals, hashCode y toString", "Es más rápida de compilar"),
                correctIndex = 2
            ),
            Question(
                id = 10,
                title = "¿Qué operador se usa para llamadas seguras (safe call) en objetos que pueden ser nulos?",
                options = listOf("!!", "?.", "?:", "as?"),
                correctIndex = 1
            ),
            Question(
                id = 11,
                title = "En Compose, ¿qué función se usa para preservar el estado durante la recomposición?",
                options = listOf("remember", "save", "persist", "hold"),
                correctIndex = 0
            ),
            Question(
                id = 12,
                title = "¿Cuál de estas funciones de alcance (Scope Functions) usa 'it' como argumento y devuelve el resultado de la lambda?",
                options = listOf("apply", "with", "let", "also"),
                correctIndex = 2
            ),
            Question(
                id = 13,
                title = "¿Cómo se crea una lista de solo lectura en Kotlin?",
                options = listOf("arrayListOf()", "mutableListOf()", "listOf()", "newList()"),
                correctIndex = 2
            ),
            Question(
                id = 14,
                title = "¿Dónde se define el constructor primario de una clase en Kotlin?",
                options = listOf("Dentro del cuerpo de la clase", "En el método init", "En la cabecera de la clase", "En un archivo aparte"),
                correctIndex = 2
            ),
            Question(
                id = 15,
                title = "¿Qué permite la palabra clave 'lateinit'?",
                options = listOf("Inicializar variables val", "Diferir la inicialización de propiedades no nulas", "Crear variables globales", "Optimizar el recolector de basura"),
                correctIndex = 1
            )
        )
    }
}
