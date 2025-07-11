package com.example.exercises.Ball

import android.opengl.ETC1.getHeight
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.exercises.R
import com.example.exercises.ui.theme.ExercisesTheme
import android.view.View
import android.os.Build
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.exercises.dicee.DiceeViewModel
import kotlin.random.Random

class BallPage : ComponentActivity() {

    // This creates a ViewModel scoped to this activity
    private val ballViewModel: BallViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        setContent {
            ExercisesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Ball(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun Ball(modifier: Modifier = Modifier) {
        val ballImages = listOf(
            R.drawable.ball1,
            R.drawable.ball2,
            R.drawable.ball3,
            R.drawable.ball4,
            R.drawable.ball5
        )
        val selectedBall by ballViewModel.selectedBall.collectAsState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = ballImages[selectedBall]),
                contentDescription = "dice image",
                modifier = Modifier.clickable { ballViewModel.answer() }
            )
        }
    }

    private fun setFullScreen() {
        window.decorView.post {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.hide(
                    android.view.WindowInsets.Type.statusBars() or
                            android.view.WindowInsets.Type.navigationBars()
                )
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        )
            }
        }
        enableEdgeToEdge()
    }
}