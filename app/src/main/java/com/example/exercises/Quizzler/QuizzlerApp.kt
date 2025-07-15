package com.example.exercises.Quizzler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import com.example.exercises.ui.theme.ExercisesTheme

class QuizzlerApp : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExercisesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizzlerScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun QuizzlerScreen(modifier: Modifier = Modifier) {
        val _questionsAndAnswers = mapOf(
            "You can lead a cow down stairs but not up stairs." to false,
            "Approximately one quarter of human bones are in the feet." to true,
            "A slug's blood is green." to true,
           "Some cats are actually allergic to humans" to true,
           "You can lead a cow down stairs but not up stairs." to false,
           "Buzz Aldrin\'s mother\'s maiden name was \"Moon\"." to true,
           "It is illegal to pee in the Ocean in Portugal." to true,
            "No piece of square dry paper can be folded in half more than 7 times." to false,
            "In London, UK, if you happen to die in the House of Parliament, you are technically entitled to a state funeral, because the building is considered too sacred a place."
                    to true,
            "The loudest sound produced by any animal is 188 decibels. That animal is the African Elephant" to false,
            "The total surface area of two human lungs is approximately 70 square metres." to true,
            "Google was originally called \"Backrub\"." to true,
            "Chocolate affects a dog\'s heart and nervous system; a few ounces are enough to kill a small dog." to true,
            "In West Virginia, USA, if you accidentally hit an animal with your car, you are free to take it home to eat." to true
        )
        var _index by remember { mutableIntStateOf(0) }
        val _scoreKeeper = remember { mutableStateListOf<Boolean>() }
        val question = _questionsAndAnswers.keys.toList()[_index];
        var showDialog by remember { mutableStateOf(false) } // State for dialog visibility

        fun processAnswer(isCorrect: Boolean) {
            _scoreKeeper.add(isCorrect)
            if (_index < _questionsAndAnswers.size - 1) _index++
            else showDialog = true

        }

        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = question,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            val answer = _questionsAndAnswers.getValue(question)
                            processAnswer(answer == true)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Green
                        ),
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(text = "True")
                    }
                    Button(
                        onClick = {
                            val answer = _questionsAndAnswers.getValue(question)
                            processAnswer(answer == false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(text = "False")
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 24.dp)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        if (_scoreKeeper.isEmpty()) {
                            Text(
                                text = "Your score will appear here",
                                modifier = Modifier.padding(all = 8.dp), // Add some padding to the placeholder
                                color = Color.White // Or your desired placeholder color
                            )
                        } else {
                            _scoreKeeper.forEach { answerCorrect ->
                                Icon(
                                    imageVector = if (answerCorrect) Icons.Default.Check else Icons.Default.Close,
                                    contentDescription = if (answerCorrect) "Correct" else "Incorrect",
                                    tint = if (answerCorrect) Color.Green else Color.Red,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
            if (showDialog) {
                val correctAnswersCount = _scoreKeeper.count { it } // Counts true values
                AlertDialog(
                    title = { Text(text = "Quiz Completed!") },
                    text = { Text(text = "You got $correctAnswersCount out of ${_scoreKeeper.size} correct.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                                _index = 0
                                _scoreKeeper.clear()
                            }
                        ) { Text("Okay") }
                    },
                    dismissButton = null,
                    onDismissRequest = {
                    },
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ExercisesTheme {
            QuizzlerScreen()
        }
    }
}