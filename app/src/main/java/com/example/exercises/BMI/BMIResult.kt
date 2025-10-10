package com.example.exercises.BMI

import android.R
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.exercises.ui.theme.ExercisesTheme
import com.example.exercises.ui.theme.Typography
import com.example.exercises.ui.theme.kBMITextStyle

@Composable
fun ResultScreen(modifier: Modifier = Modifier, viewModel: BMIViewModel, navigateBack: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color(0xFF0A0E21))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // --- Title section ---
            Text(
                text = "Your Result",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 8.dp)
                    .weight(1f),
                color = Color.White,
                style = Typography.titleLarge
            )

            // --- Result card ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF101633)),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Score:", color = Color.White)//, style = kResultTextStyle)
                    Text(viewModel.calculateBMI().toString(), style = kBMITextStyle)
                    Text(
                        viewModel.getInterpretation(),
                        color = Color.White,
//                                    style = kBodyTextStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // --- Recalculate Button ---
            Button(
                onClick = navigateBack, //onRecalculate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonColors(
                    containerColor = Color(0xFFEB1555),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Gray
                )
            ) {
                Text("Re-Calculate")
            }
        }
    }
}