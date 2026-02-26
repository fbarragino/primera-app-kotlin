package com.example.app_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_kotlin.trivia.QuizUiState
import com.example.app_kotlin.trivia.QuizViewModel
import com.example.app_kotlin.ui.theme.AppkotlinTheme

class TriviaAppActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppkotlinTheme {

                val viewModel : QuizViewModel = viewModel()

                val state = viewModel.uiState.collectAsStateWithLifecycle().value

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    "Trivia App",
                                    color = Color.White
                                )
                            },
                            navigationIcon = {
                                IconButton( onClick = { finish() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                        contentDescription = "Volver",
                                        tint = Color.White
                                    )
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFF1E88E5)
                            )
                        )
                    },
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        if(state.isFinished) {
                            FinishedScreen(
                                score = state.score,
                                total = state.questions.size * 100
                            )
                        } else {
                            QuestionScreen(
                                state = state,
                                onSelectedOption = viewModel::onSelectedOption,
                                onConfirm = viewModel::onConfirmAnswer,
                                onNext = viewModel::onNextQuestion
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionScreen(
    state : QuizUiState,
    onSelectedOption: (Int) -> Unit,
    onConfirm: () -> Unit,
    onNext: () -> Unit
) {

    val q = state.currentQuestion ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Pregunta ${state.currentIndex + 1} de ${state.questions.size}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = q.title,
            style = MaterialTheme.typography.headlineSmall
        )

        q.options.forEachIndexed{ index, option ->
            val isSelected = state.selectedIndex == index

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !state.showNextButton) { onSelectedOption(index) } ,
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = if (isSelected) 14.dp else 1.dp
                ),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { if (!state.showNextButton) onSelectedOption(index) },
                        enabled = !state.showNextButton
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Feedback Section
        AnimatedVisibility(visible = state.feedback != null) {
            Text(
                text = state.feedback ?: "",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (state.feedback?.contains("✅") == true) Color(0xFF4CAF50) else Color(0xFFF44336)
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (!state.showNextButton) {
            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.selectedIndex != null
            ) {
                val buttonText = if (state.isLastQuestion) "Ver resultados" else "Confirmar"
                Text(buttonText)
            }
        } else {
            Button(
                onClick = onNext,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E88E5))
            ) {
                Text(if (state.isLastQuestion) "Finalizar" else "Siguiente pregunta")
            }
        }

    }
}

@Composable
fun FinishedScreen(
    score: Int,
    total: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Quiz finalizado!",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Puntaje Final",
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = "$score / $total",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { /* Implementar reinicio si se desea */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Inicio")
        }
    }
}
