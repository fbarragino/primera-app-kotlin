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
                title = "¿Qué característica de Kotlin permite usar librerías de Java directamente?",
                options = listOf("Inferencia", "Interoperabilidad", "Null Safety", "Extensión"),
                correctIndex = 1
            ),
            Question(
                id = 6,
                title = "Según el glosario, ¿qué componente sobrevive a la rotación de pantalla?",
                options = listOf("Activity", "LiveData", "ViewModel", "Repository"),
                correctIndex = 2
            ),
            Question(
                id = 7,
                title = "Para añadir funciones a una clase sin heredar de ella se usan funciones de:",
                options = listOf("Alcance", "Orden superior", "Extensión", "Infix"),
                correctIndex = 2
            ),
            Question(
                id = 8,
                title = "¿Cómo se llama el sistema que evita los NullPointerExceptions en Kotlin?",
                options = listOf("Safe Check", "Null Safety", "Zero Null", "Type Guard"),
                correctIndex = 1
            ),
            Question(
                id = 9,
                title = "Clase que genera automáticamente equals(), hashCode() y toString():",
                options = listOf("Inner Class", "Sealed Class", "Enum Class", "Data Class"),
                correctIndex = 3
            ),
            Question(
                id = 10,
                title = "¿Qué componente observable actualiza la UI automáticamente al cambiar los datos?",
                options = listOf("Context", "LiveData", "Intent", "Manifest"),
                correctIndex = 1
            ),
            Question(
                id = 11,
                title = "Patrón que separa la lógica de presentación en un 'Presentador':",
                options = listOf("MVC", "MVVM", "MVP", "Repository"),
                correctIndex = 2
            ),
            Question(
                id = 12,
                title = "Función de alcance ideal para ejecutar código sobre un objeto opcional (nulo):",
                options = listOf("apply", "also", "let", "run"),
                correctIndex = 2
            ),
            Question(
                id = 13,
                title = "Modificador para inicializar variables no nulas después de su declaración:",
                options = listOf("lazy", "lateinit", "init", "after"),
                correctIndex = 1
            ),
            Question(
                id = 14,
                title = "Capa que centraliza el acceso a datos para el ViewModel:",
                options = listOf("ViewBinding", "Repository", "Activity", "Companion"),
                correctIndex = 1
            ),
            Question(
                id = 15,
                title = "Se usa para crear miembros estáticos dentro de una clase en Kotlin:",
                options = listOf("Static Object", "Companion Object", "Global Object", "Singleton"),
                correctIndex = 1
            )
        )
    }
}
