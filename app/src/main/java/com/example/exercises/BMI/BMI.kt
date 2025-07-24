package com.example.exercises.BMI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.exercises.R
import com.example.exercises.ui.theme.ExercisesTheme

class BMI : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExercisesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("BMI Calculator") },
                        )
                    },
                ) { padding ->
                    BmiCalculator(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(color = Color(0xFF0A0E21))
                    )
                }
            }
        }
    }

//    const kActiveCardColour = Color(0xFF101633);
//    const kInactiveCardColour = Color(0xFF111320);
//    const kBottomContainerColour = Color(0xFFEB1555);

    @Composable
    fun BmiCalculator(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize().padding(16.dp)
                .background(color = Color(0xFF0A0E21)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            Color(0xFF111320),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_male),
                        contentDescription = "Man fuck you",
                        tint = Color.White,             // Important!
                        modifier = Modifier.size(144.dp)) // Set a visible size)

                                Spacer (modifier = Modifier.padding(20.dp))

                                Text ("Male", color = Color.White)

                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            Color(0xFF1D1E33),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_female),
                        contentDescription = "Man fuck you",
                        tint = Color.White,             // Important!
                        modifier = Modifier.size(144.dp)) // Set a visible size)

                    Spacer (modifier = Modifier.padding(20.dp))

                    Text ("Female", color = Color.White)
                }
            }
            Box(modifier = Modifier.fillMaxWidth().weight(1f).background(Color(0xFF111320), shape = RoundedCornerShape(10.dp)))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(
                            Color(0xFF111320),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_male),
                        contentDescription = "Man fuck you",
                        tint = Color.White,             // Important!
                        modifier = Modifier.size(144.dp)) // Set a visible size)

                    Spacer (modifier = Modifier.padding(20.dp))

                    Text ("Male", color = Color.White)

                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(
                            Color(0xFF1D1E33),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_female),
                        contentDescription = "Man fuck you",
                        tint = Color.White,             // Important!
                        modifier = Modifier.size(144.dp)) // Set a visible size)

                    Spacer (modifier = Modifier.padding(20.dp))

                    Text ("Female", color = Color.White)
                }
            }
        }
    }
}