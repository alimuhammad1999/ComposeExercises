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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
                .fillMaxSize()
                .padding(16.dp)
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
                        modifier = Modifier.size(144.dp)
                    ) // Set a visible size)

                    Spacer(modifier = Modifier.padding(20.dp))

                    Text("Male", color = Color.White)

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
                        modifier = Modifier.size(144.dp)
                    ) // Set a visible size)

                    Spacer(modifier = Modifier.padding(20.dp))

                    Text("Female", color = Color.White)
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF111320), shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                var height by remember { mutableFloatStateOf(180f) }

                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Height: ${height.toInt()} cm",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    HeightSlider(height, onHeightChange = { height = it })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                var age by remember { mutableIntStateOf(25) }
                var weight by remember { mutableIntStateOf(60) }

                RoundSelectorCard(
                    label = "AGE",
                    value = age,
                    onIncrement = { age++ },
                    onDecrement = { if (age > 1) age-- },
                    modifier = Modifier.weight(1f)
                )

                RoundSelectorCard(
                    label = "WEIGHT",
                    value = weight,
                    onIncrement = { weight++ },
                    onDecrement = { if (weight > 1) weight-- },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    @Composable
    fun HeightSlider(
        height: Float,
        onHeightChange: (Float) -> Unit,
        modifier: Modifier = Modifier,
        min: Float = 120f,
        max: Float = 240f,
        trackColor: Color = Color.Gray,
        activeTrackColor: Color = Color.White,
        thumbColor: Color = Color(0xFFEB1555),
        overlayColor: Color = Color(0x29EB1555) // Similar overlay effect
    ) {
        Slider(
            value = height,
            onValueChange = onHeightChange,
            valueRange = min..max,
            modifier = modifier.padding(horizontal = 16.dp),
            colors = SliderDefaults.colors(
                thumbColor = thumbColor,
                activeTrackColor = activeTrackColor,
                inactiveTrackColor = overlayColor,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent,
                disabledActiveTrackColor = Color.LightGray,
                disabledThumbColor = Color.Gray
            )
        )
    }

    /*@Composable
    fun HeightSlider(
        value: Float,
        onHeightChange: (Float) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Slider(
            value = value,
            onValueChange = onHeightChange,
            valueRange = 120f..240f,
            modifier = modifier.padding(horizontal = 16.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color(0xFFEB1555),
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.Gray
            )
        )
    }*/

    @Composable
    fun RoundSelectorCard(
        label: String,
        value: Int,
        onIncrement: () -> Unit,
        onDecrement: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFF111320))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$value",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 72.sp, color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                RoundIconButton(
                    icon = Icons.Default.KeyboardArrowDown,
                    onClick = onDecrement
                )
                Spacer(modifier = Modifier.width(12.dp))
                RoundIconButton(
                    icon = Icons.Default.KeyboardArrowUp,
                    onClick = onIncrement
                )
            }
        }
    }

    @Composable
    fun RoundIconButton(
        icon: ImageVector,
        contentDescription: String? = null,
        backgroundColor: Color = Color(0xFF4C4F5E).copy(alpha = 0.8f),
        iconTint: Color = Color.White,
        size: Dp = 42.dp,
        onClick: (() -> Unit)? = null
    ) {
        val haptic = LocalHapticFeedback.current

        Surface(
            shape = CircleShape,
            color = backgroundColor,
            modifier = Modifier.size(size),
            tonalElevation = 4.dp // Optional shadow-like effect
        ) {
            IconButton(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick?.invoke()
                },
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = iconTint,
                    modifier = Modifier.size(size / 2) // Adjust icon size proportionally
                )
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