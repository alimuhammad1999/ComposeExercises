package com.example.exercises.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
//     Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

val kTempTextStyle = TextStyle(
    fontFamily = FontFamily.Serif,
    fontSize = 100.sp
)

val kMessageTextStyle = TextStyle(
    fontFamily = FontFamily.Serif,
    fontSize = 60.sp
)

val kButtonTextStyle = TextStyle(
    fontSize = 30.sp,
    fontFamily = FontFamily.Serif
)

val kConditionTextStyle = TextStyle(
    fontSize = 100.sp
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KInputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String = "Enter City Name"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint, color = Color.Gray) },
        modifier = Modifier.padding(8.dp),
        singleLine = true,
        colors = TextFieldDefaults
            .outlinedTextFieldColors(containerColor = Color.White),
        shape = RoundedCornerShape(35.dp)
    )
}


val kBMITextStyle = TextStyle(
    fontSize = 100.sp,
    color = Color.White,
    fontWeight = FontWeight.Bold
)