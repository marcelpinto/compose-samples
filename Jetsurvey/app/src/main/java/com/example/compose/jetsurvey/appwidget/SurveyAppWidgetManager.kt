package com.example.compose.jetsurvey.appwidget

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.example.compose.jetsurvey.survey.SurveyRepository

class SurveyAppWidgetManager(private val context: Context) {

    private val manager = GlanceAppWidgetManager(context)

    suspend fun reset() {
        updateQuestionIndex(-1)
    }

    suspend fun updateQuestionIndex(index: Int) {
        val widget = SurveyAppWidget(SurveyRepository.getSurvey())
        manager.getGlanceIds(SurveyAppWidget::class.java).forEach { glanceId ->
            widget.updateAppWidgetState<Preferences>(context, glanceId) {
                it.toMutablePreferences().apply {
                    clear()
                    set(SurveyAppWidget.indexKey, index)
                }
            }
            widget.update(context, glanceId)
        }
    }
}