package com.example.compose.jetsurvey.appwidget

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.Button
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.*
import androidx.glance.appwidget.unit.ColorProvider
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.compose.jetsurvey.Arguments
import com.example.compose.jetsurvey.MainActivity
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
            SurveyHomepage(survey.title)
        }
    }
}

@Composable
private fun stringResource(stringId: Int) = LocalContext.current.resources.getString(stringId)

@Composable
private fun ColumnScope.SurveyHomepage(@StringRes title: Int) {
    Text(
        text = stringResource(title),
        style = SurveyAppWidgetTheme.titleStyle,
        modifier = GlanceModifier.fillMaxWidth().padding(bottom = 32.dp)
    )
    Button(
        text = "START QUIZ",
        modifier = GlanceModifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                day = SurveyAppWidgetTheme.colorAccentDay,
                night = SurveyAppWidgetTheme.colorAccentNight
            ),
        onClick = actionStartActivity<MainActivity>(
            actionParametersOf(
                ActionParameters.Key<Int>(Arguments.CurrentIndex) to 0
            )
        ),
        style = TextStyle(
            color = ColorProvider(day = Color.White, night = Color.Black),
            fontWeight = FontWeight.Bold
        )
    )
}

class SurveyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SurveyAppWidget(SurveyRepository.getSurvey())
}