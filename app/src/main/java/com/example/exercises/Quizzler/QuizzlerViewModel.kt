package com.example.exercises.Quizzler

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class QuizzlerViewModel : ViewModel() {
    // Using a Map to store questions and their corresponding answers.
    // This makes it easier to manage and access the correct answer for a given question.
    private val _questionsAndAnswers = mapOf(
        "You can lead a cow down stairs but not up stairs." to false,
        "Approximately one quarter of human bones are in the feet." to true,
        "A slug's blood is green." to true
    )
    // Expose only the questions (keys of the map) to the UI if needed separately.
    private val _questionsList = _questionsAndAnswers.keys.toList()

    private val _scoreKeeper = MutableStateFlow<List<Boolean>>(listOf())
    private val _index = MutableStateFlow(0)

    fun getQuestion(): String {
        if(_index.value >= _questionsList.size) _index.value = 0;
        return _questionsList[_index.value]
    }
}