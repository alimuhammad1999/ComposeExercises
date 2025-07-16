package com.example.exercises.Quizzler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.runtime.collectAsState
import com.example.exercises.ui.theme.ExercisesTheme

class QuizzlerApp : ComponentActivity() {

    private val quizzlerViewModel : QuizzlerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExercisesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizzlerScreen(modifier = Modifier.padding(innerPadding), quizzlerViewModel)
                }
            }
        }
    }

    @Composable
    fun QuizzlerScreen(modifier: Modifier = Modifier, viewModel: QuizzlerViewModel) {

        val scoreKeeper by viewModel.scoreKeeper.collectAsState()
        val question by viewModel.currentQuestion.collectAsState() // uses index internally
        val showDialog by viewModel.showDialog.collectAsState()

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
                        onClick = { viewModel.submitAnswer(true) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(text = "True")
                    }
                    Button(
                        onClick = { viewModel.submitAnswer(false) },
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
                        if (scoreKeeper.isEmpty()) {
                            Text(
                                text = "Your score will appear here",
                                modifier = Modifier.padding(all = 8.dp), // Add some padding to the placeholder
                                color = Color.White // Or your desired placeholder color
                            )
                        } else {
                            scoreKeeper.forEach { answerCorrect ->
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
                val correctAnswersCount = scoreKeeper.count { it } // Counts true values
                AlertDialog(
                    title = { Text(text = "Quiz Completed!") },
                    text = { Text(text = "You got $correctAnswersCount out of ${scoreKeeper.size} correct.") },
                    confirmButton = {
                        TextButton( onClick = { viewModel.resetQuiz() } ) { Text("Okay") }
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
            QuizzlerScreen(Modifier, quizzlerViewModel)
        }
    }
}