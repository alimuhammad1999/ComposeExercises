package com.example.exercises.dicee

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DiceeViewModel : ViewModel() {
    private val _dice1 = MutableStateFlow(0)
    val dice1: StateFlow<Int> = _dice1

    private val _dice2 = MutableStateFlow(0)
    val dice2: StateFlow<Int> = _dice2

    fun rollDice1() {
        _dice1.value = (0..5).random()
    }

    fun rollDice2() {
        _dice2.value = (0..5).random()
    }
}