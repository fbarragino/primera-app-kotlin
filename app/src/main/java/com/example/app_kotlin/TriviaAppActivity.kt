package com.example.app_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
                            // Vista FinishedScreen
                            FinishedScreen()
                        } else {
                            // Vista/Pantalla QuestionScreen
                            QuestionScreen(
                                state = state,
                                onSelectedOption = viewModel::onSelectedOption
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
    onSelectedOption: (Int) -> Unit
) {

    // Tomare la pregunta actual desde el estado (derivado)
    val q = state.currentQuestion ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Pregunta 1 de N
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
                    .clickable { onSelectedOption(index) } ,
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = if (isSelected) 14.dp else 1.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isSelected,
                        onClick = { onSelectedOption(index) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar")
        }

    }
}

@Composable
fun FinishedScreen() {

}