package com.example.exercises.BMI

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BMIViewModel : ViewModel() {
    private var _male = MutableStateFlow(true)
    val male : StateFlow<Boolean> = _male

    private var _height = MutableStateFlow(180f)
    val height : StateFlow<Float> = _height

    private var _weight = MutableStateFlow(60)
    val weight : StateFlow<Int> = _weight

    private var _age = MutableStateFlow(25)
    val age : StateFlow<Int> = _age

    fun setMale(boolean: Boolean) {
        _male.value = boolean
    }

    fun setHeight(height : Float) {
        _height.value = height
    }

    fun setWeight(int: Int) {
        _weight.value += int
    }

    fun setAge(int: Int) {
        _age.value += int
    }

    fun calculateBMI() {
        val heightInMeters = height.value / 100f
        val bmi = weight.value / (heightInMeters * heightInMeters)
//        return bmi
    }

    class BMICalculator(val height: Int, val weight: Float) {
        fun calculateBMI(): Float {
            return weight / ((height / 100f) * (height / 100f))
        }

        fun getResult(): String {
            val bmi = calculateBMI()
            return when {
                bmi >= 25 -> "Overweight"
                bmi > 18.5 -> "Normal"
                else -> "Underweight"
            }
        }
    }

}