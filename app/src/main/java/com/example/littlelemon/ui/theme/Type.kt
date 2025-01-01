package com.example.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.littlelemon.R

// Set of Material typography styles to start with

val MarkaziText = FontFamily(
    Font(R.font.markazi_regular, FontWeight.Normal),
    Font(R.font.markazi_bold, FontWeight.Bold),
    Font(R.font.markazi_medium, FontWeight.Medium),
)

val Karla = FontFamily(
    Font(R.font.karla_regular, FontWeight.Normal),
    Font(R.font.karla_bold, FontWeight.Bold),
    Font(R.font.karla_medium, FontWeight.Medium),
    Font(R.font.karla_semibold, FontWeight.SemiBold),
    Font(R.font.karla_extrabold, FontWeight.ExtraBold),
    Font(R.font.karla_extralight, FontWeight.ExtraLight),
    Font(R.font.karla_light, FontWeight.Light),
)

val baseline = Typography()
val Typography = Typography(
    displayLarge = baseline.displayLarge.copy(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Medium,
        fontSize = 64.sp,
        lineHeight = 50.sp
    ),
    displayMedium = baseline.displayMedium.copy(
        fontFamily = MarkaziText,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp
    ),
    displaySmall = baseline.displaySmall.copy(fontFamily = MarkaziText),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = MarkaziText),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = MarkaziText),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = MarkaziText),
    titleLarge = baseline.titleLarge.copy(
        fontFamily = Karla,
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
    ),
    titleMedium = baseline.titleMedium.copy(
        fontFamily = Karla,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    titleSmall = baseline.titleSmall.copy(fontFamily = Karla),
    bodyLarge = baseline.bodyLarge.copy(
        fontFamily = Karla,
    ),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = Karla),
    bodySmall = baseline.bodySmall.copy(fontFamily = Karla),
    labelLarge = baseline.labelLarge.copy(fontFamily = Karla),
    labelMedium = baseline.labelMedium.copy(fontFamily = Karla),
    labelSmall = baseline.labelSmall.copy(fontFamily = Karla),
)