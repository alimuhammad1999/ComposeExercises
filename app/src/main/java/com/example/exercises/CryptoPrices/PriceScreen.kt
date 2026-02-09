package com.example.exercises.CryptoPrices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.exercises.BMI.BMI
import com.example.exercises.BMI.BMIAppBar
import com.example.exercises.ui.theme.ExercisesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoAppBar(
    modifier: Modifier = Modifier
) {
    Surface (shadowElevation = 6.dp) {
        TopAppBar(
            title = { Text("Crypto Price Tracker", color = Color.White) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFF1F2436)
            //Color(0xFF0A0E21)
            // MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = modifier,
            /*navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            }*/
        )
    }
}
import com.example.exercises.ui.theme.ExercisesTheme

class PriceScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel : ViewModel
        setContent {
            ExercisesTheme {
                Scaffold(
//                    modifier = Modifier.fillMaxSize(),

                    topBar = {
                        CryptoAppBar() /*{
                        TopAppBar(title = { Text("BMI Calculator") })
                    }*/
                    },
                ) { padding ->
                    PriceScreen(Modifier.padding(padding))
                }
            }
        }

    }

    @Composable
    fun CurrencyDropdown(
        selectedCurrency: String,
        onCurrencySelected: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var expanded by remember { mutableStateOf(false) }

        Box {
            Text(
                text = selectedCurrency,
                modifier = modifier
                    .clickable { expanded = true }
                    .padding(16.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                currenciesList.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            onCurrencySelected(currency)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun CoinConverterScreen(modifier : Modifier = Modifier) {
        var selectedCrypto by remember { mutableStateOf("BTC") }
        var selectedCurrency by remember { mutableStateOf("USD") }
        var expandedCrypto by remember { mutableStateOf(false) }
        var expandedCurrency by remember { mutableStateOf(false) }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121212))
                .padding(16.dp),
            color = Color(0xFF121212)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Crypto Converter",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // ---------- Crypto Dropdown ----------
                Box {
                    Text(
                        text = selectedCrypto,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable { expandedCrypto = true }
                            .padding(12.dp)
                            .background(Color.DarkGray,
                                shape = MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    )

                    DropdownMenu(
                        expanded = expandedCrypto,
                        onDismissRequest = { expandedCrypto = false },
                        modifier = Modifier.width(100.dp)
//                        containerColor = Color(0xFF1E1E1E)
                    ) {
                        cryptoList.forEach { crypto ->
                            DropdownMenuItem(
                                text = { Text(crypto, color = Color.White) },
                                onClick = {
                                    selectedCrypto = crypto
                                    expandedCrypto = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ---------- Currency Dropdown ----------
                Box {
                    Text(
                        text = selectedCurrency,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable { expandedCurrency = true }
                            .padding(12.dp)
                            .background(Color.DarkGray, shape = MaterialTheme.shapes.medium)
                            .padding(12.dp)
                    )

                    DropdownMenu(
                        expanded = expandedCurrency,
                        onDismissRequest = { expandedCurrency = false },
//                        containerColor = Color(0xFF1E1E1E)
                    ) {
                        currenciesList.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency, color = Color.White) },
                                onClick = {
                                    selectedCurrency = currency
                                    expandedCurrency = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // ---------- Convert Button ----------
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
                ) {
                    Text(text = "Convert", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceScreen(modifier : Modifier = Modifier) {
    var selectedCurrency by remember { mutableStateOf("GBP") }
    var expanded by remember { mutableStateOf(false) }

    /*Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🤑 Coin Ticker") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1976D2))
            )
        },
        containerColor = Color.White
    ) { padding ->*/

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ---------- Top Card ----------
            Card(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF40C4FF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = "1 BTC = ? $selectedCurrency",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(vertical = 15.dp, horizontal = 28.dp)
                        .fillMaxWidth()
                )
            }
            ExercisesTheme {

            }
        }

    }

    @Composable
    fun CryptoPrices(modifier: Modifier = Modifier) {

            // ---------- Bottom Picker ----------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFF2196F3))
                    .padding(bottom = 30.dp),
                contentAlignment = Alignment.Center
            ) {

                // Android equivalent of DropdownPicker
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    TextField(
                        value = selectedCurrency,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Currency") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color.Gray,
                            unfocusedLabelColor = Color.Gray
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(0.8f)
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
//                        containerColor = Color.White
                    ) {
                        currenciesList.forEach { currency ->
                            DropdownMenuItem(
                                text = { Text(currency) },
                                onClick = {
                                    selectedCurrency = currency
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
//}