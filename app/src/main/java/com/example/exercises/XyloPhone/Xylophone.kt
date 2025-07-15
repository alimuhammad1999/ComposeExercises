package com.example.exercises.XyloPhone

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exercises.ProfileCard
import com.example.exercises.R
import com.example.exercises.dicee.DiceeViewModel
import com.example.exercises.ui.theme.ExercisesTheme

class Xylophone : ComponentActivity() {
    private lateinit var soundPool: SoundPool
    private val soundIds = mutableListOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExercisesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    XylophoneScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @Composable
    fun XylophoneScreen(modifier: Modifier = Modifier) {
        val keyColors = listOf(
            Color.Red,
            Color.Magenta,
            Color.Yellow,
            Color.Green,
            Color.Cyan,
            Color.Blue,
            Color(0xFF8A2BE2) // Using Color(0xFF8A2BE2) for Indigo
        )
        val rawIds = listOf(
            R.raw.note1, R.raw.note2, R.raw.note3, R.raw.note4,
            R.raw.note5, R.raw.note6, R.raw.note7
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            keyColors.forEachIndexed { index, color ->
                XylophoneKey(
                    color = color,
                    onClick = { MediaPlayer.create(this@Xylophone, rawIds[index]).start() },
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
            }
        }
    }

    @Composable
    fun XylophoneKey(
        color: Color,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(color)
                .clickable { onClick() },
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ExercisesTheme {
            XylophoneScreen()
        }
    }

}