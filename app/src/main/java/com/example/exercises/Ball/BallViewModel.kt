package com.example.exercises.Ball

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BallViewModel : ViewModel() {
    private val _selectedBall = MutableStateFlow((0..4).random())
    val selectedBall : StateFlow<Int> = _selectedBall

    fun answer() {
        _selectedBall.value = (0..4).random();
    }
}