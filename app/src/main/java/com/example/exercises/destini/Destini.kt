package com.example.exercises.destini

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exercises.R
import com.example.exercises.ui.theme.ExercisesTheme

class Destini : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            ExercisesTheme {
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DestiniStory(modifier = Modifier.padding(innerPadding), viewModel = DestiniViewModel())
                }
            }
        }
    }

    @Composable
    fun DestiniStory(modifier: Modifier, viewModel: DestiniViewModel) {
        val Scenario = viewModel.scenarios
        val index = viewModel.index.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Surface(
                modifier = Modifier.fillMaxSize().padding(vertical = 50.dp, horizontal = 20.dp),
                color = Color.Transparent // Or MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Spacer(modifier = Modifier.padding(12.dp))

                    Text(
                        modifier = Modifier.padding(top = 40.dp),
                        text = Scenario[index.value].title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.W600,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.padding(12.dp).fillMaxHeight(0.3f))

                    Column (
                        Modifier.padding(bottom = 20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .background(color = Color(0xFFF44336))
                                .clickable { viewModel.nextStory(1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                Scenario[index.value].choice1,
                                Modifier.padding(12.dp)
                                    .align(Alignment.Center),
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.padding(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .background(color = Color(0xFF2196F3))
                                .clickable { viewModel.nextStory(1) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                Scenario[index.value].choice2,
                                modifier = Modifier.padding(12.dp),
                                textAlign = TextAlign.Center

                            )
                        }
                    }
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        ExercisesTheme {
            DestiniStory(Modifier, DestiniViewModel())
        }
    }
}