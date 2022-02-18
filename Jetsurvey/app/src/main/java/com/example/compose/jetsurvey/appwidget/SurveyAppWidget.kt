package com.example.compose.jetsurvey.appwidget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.*
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import com.example.compose.jetsurvey.survey.Survey
import com.example.compose.jetsurvey.survey.SurveyRepository

class SurveyAppWidget(private val survey: Survey) : GlanceAppWidget() {

    @Composable
    override fun Content() {
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .padding(24.dp)
                .appWidgetBackground()
                .background(
                    day = SurveyAppWidgetTheme.colorBackgroundDay,
                    night = SurveyAppWidgetTheme.colorBackgroundNight
                )
                .cornerRadius(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO implement UI
        }
    }
}

class SurveyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SurveyAppWidget(SurveyRepository.getSurvey())
}