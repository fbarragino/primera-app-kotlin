package com.example.app_kotlin

import android.os.Bundle
import android.widget.RadioButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.example.app_kotlin.ui.theme.AppkotlinTheme

class TriviaAppActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppkotlinTheme {

                val state = false;

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
                        if(state) {
                            // Vista FinishedScreen
                            FinishedScreen()
                        } else {
                            // Vista/Pantalla QuestionScreen
                            QuestionScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Pregunta 1 de N
        Text(
            text = "Pregunta 1 de 5",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Pregunta 1 ABC",
            style = MaterialTheme.typography.headlineSmall
        )

        repeat(4) { index ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = false,
                    onClick = {}
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Respuesta ${index + 1} de la pregunta 1",
                    style = MaterialTheme.typography.bodyLarge
                )
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