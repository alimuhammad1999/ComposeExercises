package com.example.exercises.CryptoPrices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.exercises.ui.theme.ExercisesTheme

class PriceScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel : ViewModel
        setContent {
            ExercisesTheme {

            }
        }

    }

    @Composable
    fun CryptoPrices(modifier: Modifier = Modifier) {

    }
}