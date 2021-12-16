package com.example.compose.jetsurvey.appwidget

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.glance.appwidget.unit.ColorProvider
import androidx.glance.text.FontStyle
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle

object SurveyAppWidgetTheme {
    val colorAccentDay = Color(0xFF8101EF)
    val colorAccentNight = Color(0xFFBA65F1)
    val colorBackgroundDay = Color.White
    val colorBackgroundNight = Color.Black
    val colorTextDay = Color(0xff232323)
    val colorTextNight = Color(0xFFE0E0E0)

    val colorTextDayNight = ColorProvider(day = colorTextDay, night = colorTextNight)

    val titleStyle = TextStyle(
        fontSize = 16.sp,
        color = colorTextDayNight,
        fontWeight = FontWeight.Bold,
    )
    val subtitleStyle = TextStyle(
        fontSize = 12.sp,
        color = ColorProvider(day = colorTextDay, night = Color(0xFFB6B6B6)),
        fontStyle = FontStyle.Italic
    )
}