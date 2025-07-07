package com.example.exercises.dicee

import android.content.res.Configuration
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exercises.R
import com.example.exercises.ui.theme.ExercisesTheme

class Dicee : ComponentActivity() {

    // This creates a ViewModel scoped to this activity
    private val diceeViewModel: DiceeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExercisesTheme {
                DiceeScreen(diceeViewModel)
            }
        }
    }

    @Composable
    fun DiceeScreen(viewModel: DiceeViewModel) {
        val tealColor = MaterialTheme.colorScheme.primary
        val diceImages = listOf(
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6
        )
        val dice1 by viewModel.dice1.collectAsState()
        val dice2 by viewModel.dice2.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = tealColor
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                DiceImage(
                    imageResId = diceImages[dice1],
                    onImageClick = { viewModel.rollDice1() }
                )

                Spacer(modifier = Modifier.size(30.dp))

                DiceImage(
                    imageResId = diceImages[dice2],
                    onImageClick = { viewModel.rollDice2() }
                )
            }
        }
    }

    @Composable
    fun DiceImage(
        imageResId: Int,
        onImageClick: () -> Unit
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "dice image",
            modifier = Modifier
                .fillMaxHeight(getHeight())
                .clickable { onImageClick() }
        )
    }

    @Composable
    fun getHeight() : Float {
        val portrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
        return if (portrait) 0.2f else 0.8f
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        val dummyViewModel = DiceeViewModel().apply {
            // set dummy state if needed
        }
        ExercisesTheme {
            DiceeScreen(dummyViewModel)
        }
    }
}