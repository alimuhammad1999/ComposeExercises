package com.example.exercises.Clima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import com.example.exercises.R
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.exercises.BMI.BMI
import com.example.exercises.BMI.BMI.BMIScreens
import com.example.exercises.BMI.BMIAppBar
import com.example.exercises.ui.theme.ExercisesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: WeatherAppScreens,
    /*canNavigateBack: Boolean,
   navigateUp: () -> Unit,
   modifier: Modifier = Modifier*/
) {
    Surface (shadowElevation = 6.dp) {
        TopAppBar(
            title = { Text(currentScreen.title, color = Color.White) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFF1F2436)//Color(0xFF0A0E21)//MaterialTheme.colorScheme.primaryContainer
            ),
//            modifier = modifier,
            navigationIcon = {
//                if (canNavigateBack) {
//                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
//                    }
//                }
            }
        )
    }
}

enum class WeatherAppScreens (val title : String) {
    City("City"),
}

class CityScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = WeatherViewModel();
        setContent {
            val navController = rememberNavController()
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentScreen = WeatherAppScreens.valueOf(backStackEntry?.destination?.route ?:
            WeatherAppScreens.City.name)
            ExercisesTheme {
                Scaffold (
                    topBar =  {AppBar(currentScreen) }
                ) {
                    padding ->
                    NavHost(navController, startDestination = WeatherAppScreens.City.name) {
                        composable(WeatherAppScreens.City.name) {
                            CityScreen(Modifier.padding(padding)
                                , weatherViewModel = viewModel)
                        }
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
        CityScreen(Modifier, null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityScreen(
    modifier: Modifier,
//    navController: NavController,
    weatherViewModel: WeatherViewModel? // if you're using MVVM pattern
) {
    var cityName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id =  R.drawable.city_background),
            contentDescription = "City Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Back Button
            IconButton(
                onClick = { /*navController.popBackStack()*/ },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // TextField for City Name
            OutlinedTextField(
                value = cityName,
                onValueChange = { cityName = it },
                placeholder = { Text("Enter City Name", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                shape = RoundedCornerShape(35.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Button to Get Weather
            Button(
                onClick = {
                    weatherViewModel?.getWeatherByCity(cityName)
//                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Get Weather", fontSize = 20.sp, color = Color.White)
            }
        }
    }
}