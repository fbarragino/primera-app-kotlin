package com.example.app_kotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app_kotlin.ui.theme.AppkotlinTheme

class TodoAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppkotlinTheme {
                TodoApp(onBack = { finish() })
            }
        }
    }
}

data class TodoItem(
    val id: Int,
    val text: String,
    val done: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(onBack: () -> Unit) {

    val context = LocalContext.current

    //  LISTA CON TAREAS PREDETERMINADAS
    val todos = remember {
        mutableStateListOf(
            TodoItem(id = 1, text = "Aprender Kotlin", done = true),
            TodoItem(id = 2, text = "Finalizar el curso"),
        )
    }

    var newTask by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo App", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFC8EAC)
                )
            )
        },

        //  AGREGAR TAREA
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                    if (newTask.isNotBlank()) {

                        todos.add(
                            TodoItem(
                                id = (todos.maxOfOrNull { it.id } ?: 0) + 1,
                                text = newTask
                            )
                        )

                        newTask = ""

                        Toast.makeText(
                            context,
                            "Tarea agregada",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar tarea")
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // FONDO
            Image(
                painter = painterResource(R.drawable.fondo_todo),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // CONTENIDO
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // INPUT
                OutlinedTextField(
                    value = newTask,
                    onValueChange = { newTask = it },
                    label = { Text("Nueva tarea") },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "Listado",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    items(todos, key = { it.id }) { task ->

                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                // Cambiar estado (tachar)
                                .clickable {
                                    val index = todos.indexOf(task)
                                    todos[index] = task.copy(done = !task.done)
                                }
                        ) {

                            Box {

                                Image(
                                    painter = painterResource(R.drawable.sparkle),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = task.text,
                                        modifier = Modifier.weight(1f),
                                        textDecoration =
                                            if (task.done)
                                                androidx.compose.ui.text.style.TextDecoration.LineThrough
                                            else
                                                androidx.compose.ui.text.style.TextDecoration.None
                                    )

                                    // ELIMINAR TAREA
                                    IconButton(
                                        onClick = {
                                            todos.remove(task)

                                            Toast.makeText(
                                                context,
                                                "Tarea eliminada",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Clear,
                                            contentDescription = "Eliminar"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}