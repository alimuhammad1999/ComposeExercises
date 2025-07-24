package com.example.exercises.BMI

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    Text("Weight", color = Color.White)

                    Text("70",
                        style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 100.sp, color = Color.White
                    ))

                    Row (
                        modifier = Modifier.fillMaxWidth(0.7f),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    color = Color(0xFF4C4F5E).copy(alpha = 0.8f),
                                    shape = CircleShape
                                )
                                .clickable {
                                    // your onClick action
                                }
                                .padding(16.dp), // inner padding inside circle
                            tint = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Add",
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    color = Color(0xFF4C4F5E).copy(alpha = 0.8f),
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                                .clickable {
                                    // your onClick action
                                }
                                .padding(16.dp), // inner padding inside circle
                            tint = Color.White
                        )
                        RoundButton(icon = Icons.Default.KeyboardArrowDown)
                    }

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

    @Composable
    fun RoundButton(
        icon: ImageVector,
        contentDescription: String? = null,
        backgroundColor: Color = Color(0xFF4C4F5E).copy(alpha = 0.8f),
        iconTint: Color = Color.White,
        size: Dp = 56.dp,
        onClick: (() -> Unit)? = null
    ) {
        val haptic = LocalHapticFeedback.current
        var pressed by remember { mutableStateOf(false) }

        val scale by animateFloatAsState(
            targetValue = if (pressed) 0.9f else 1f,
            animationSpec = tween(durationMillis = 100),
            label = "scaleAnimation"
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .clip(CircleShape)
                .background(backgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            pressed = true
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            tryAwaitRelease()
                            pressed = false
                            if (onClick != null) onClick()
                        }
                    )
                }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint
            )
        }
    }

}