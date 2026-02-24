package com.example.app_kotlin.trivia

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        value = QuizUiState(

        )


}