package com.example.exercises.Quizzler

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class QuizUiState(
    val currentQuestionText: String = "",
    val scoreKeeper: List<Boolean> = emptyList(),
    val isQuizOver: Boolean = false,
    val totalQuestions: Int = 0,
    val correctAnswers: Int = 0,
    val currentQuestionIndex: Int = 0 // Optional, if UI needs it
)

class QuizzlerViewModel : ViewModel() {
    private val _questionsAndAnswers = linkedMapOf(
        "You can lead a cow down stairs but not up stairs." to false,
        "Approximately one quarter of human bones are in the feet." to true,
        "A slug's blood is green." to true,
        "Some cats are actually allergic to humans" to true,
        "Buzz Aldrin\'s mother\'s maiden name was \"Moon\"." to true,
        "It is illegal to pee in the Ocean in Portugal." to true,
        "No piece of square dry paper can be folded in half more than 7 times." to false,
        "In London, UK, if you happen to die in the House of Parliament, " +
                "you are technically entitled to a state funeral, " +
                "because the building is considered too sacred a place."
                to true,
        "The loudest sound produced by any animal is 188 decibels. " +
                "That animal is the African Elephant" to false,
        "The total surface area of two human lungs is approximately 70 square metres." to true,
        "Google was originally called \"Backrub\"." to true,
        "Chocolate affects a dog\'s heart and nervous system; " +
                "a few ounces are enough to kill a small dog." to true,
        "In West Virginia, USA, if you accidentally hit an animal with your " +
                "car, you are free to take it home to eat." to true
    )

    private val _index = MutableStateFlow(0)

    private val _scoreKeeper = MutableStateFlow<List<Boolean>>(listOf())
    val scoreKeeper : StateFlow<List<Boolean>> = _scoreKeeper

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _currentQuestion = MutableStateFlow(_questionsAndAnswers.keys.toList()[0])
    val currentQuestion : StateFlow<String> = _currentQuestion

    fun submitAnswer(answer: Boolean) {
        val isCorrect = _questionsAndAnswers.getValue(currentQuestion.value) == answer
        _scoreKeeper.value = _scoreKeeper.value.toMutableList() + isCorrect

        if (_index.value < _questionsAndAnswers.size - 1) {
            _index.value++
            _currentQuestion.value = _questionsAndAnswers.keys.toList()[_index.value]
        }
        else _showDialog.value = true
    }

    fun resetQuiz() {
        _index.value = 0
        _scoreKeeper.value = listOf()
        _showDialog.value = false
        _currentQuestion.value = _questionsAndAnswers.keys.toList()[0]
    }

}