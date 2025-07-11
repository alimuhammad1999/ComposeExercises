package com.example.exercises.XyloPhone

import android.media.AudioAttributes
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
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    XylophoneScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        // 1. Initialize SoundPool
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(7)
            .setAudioAttributes(audioAttributes)
            .build()

        // 2. Load all 7 sounds
        val rawIds = listOf(
            R.raw.note1, R.raw.note2, R.raw.note3, R.raw.note4,
            R.raw.note5, R.raw.note6, R.raw.note7
        )
        rawIds.forEach {
            soundIds.add(soundPool.load(this, it, 1))
        }
    }

    @Composable
    fun XylophoneScreen(modifier: Modifier = Modifier) {
        val keyColors = listOf(
            Color.Red, Color.Magenta, Color.Yellow,
            Color.Green, Color.Cyan, Color.Blue, Color.Magenta
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            for (i in 0 until 7) {
                XylophoneKey(
                    color = keyColors[i],
                    onClick = {
                        soundPool.play(soundIds[i], 1f, 1f, 0, 0, 1f)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
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
                .height(50.dp) // <-- this is important
                .background(color)
                .clickable { onClick() }
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