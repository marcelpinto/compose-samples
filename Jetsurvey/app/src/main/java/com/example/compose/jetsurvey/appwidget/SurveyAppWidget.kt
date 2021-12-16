package com.example.compose.jetsurvey.appwidget

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.*
import androidx.glance.appwidget.*
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.ToggleableStateKey
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.unit.ColorProvider
import androidx.glance.layout.*
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.compose.jetsurvey.Arguments
import com.example.compose.jetsurvey.MainActivity
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.appwidget.SurveyAppWidget.Companion.indexKey
import com.example.compose.jetsurvey.appwidget.SurveyAppWidget.Companion.questionIndexKey
import com.example.compose.jetsurvey.appwidget.SurveyAppWidgetTheme.colorAccentDay
import com.example.compose.jetsurvey.appwidget.SurveyAppWidgetTheme.colorAccentNight
import com.example.compose.jetsurvey.appwidget.SurveyAppWidgetTheme.colorBackgroundDay
import com.example.compose.jetsurvey.appwidget.SurveyAppWidgetTheme.colorBackgroundNight
import com.example.compose.jetsurvey.appwidget.SurveyAppWidgetTheme.colorTextDayNight
import com.example.compose.jetsurvey.appwidget.SurveyAppWidgetTheme.subtitleStyle
import com.example.compose.jetsurvey.appwidget.SurveyAppWidgetTheme.titleStyle
import com.example.compose.jetsurvey.survey.PossibleAnswer
import com.example.compose.jetsurvey.survey.Question
import com.example.compose.jetsurvey.survey.Survey
import com.example.compose.jetsurvey.survey.SurveyRepository

class SurveyAppWidget(private val survey: Survey) : GlanceAppWidget() {

    companion object {
        val indexKey = intPreferencesKey(Arguments.CurrentIndex)
        val questionIndexKey = ActionParameters.Key<String>("questionIndex")
    }

    override val stateDefinition = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val currentState = currentState<Preferences>()
        val currentIndex = currentState[indexKey] ?: -1
        Column(
            modifier = GlanceModifier.fillMaxSize()
                .padding(24.dp)
                .appWidgetBackground()
                .background(
                    day = colorBackgroundDay,
                    night = colorBackgroundNight
                )
                .cornerRadius(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (currentIndex == -1) {
                SurveyHomepage(survey.title)
            } else {
                SurveyQuestionnaire(survey.questions.first())
            }
        }
    }
}

@Composable
private fun stringResource(stringId: Int) = LocalContext.current.resources.getString(stringId)

@Composable
private fun ColumnScope.SurveyHomepage(@StringRes title: Int) {
    Text(
        text = stringResource(title),
        style = titleStyle,
        modifier = GlanceModifier.fillMaxWidth().padding(bottom = 32.dp)
    )
    SurveyButton(
        text = "START QUIZ",
        onClick = actionRunCallback<SurveyNextAction>(
            actionParametersOf(indexKey.toParametersKey() to 0)
        )
    )
}

@Composable
private fun SurveyQuestionnaire(question: Question) {
    val answer = question.answer as PossibleAnswer.MultipleChoice
    LazyColumn(modifier = GlanceModifier.fillMaxSize()) {
        item {
            SurveyQuestionTitle(question.questionText)
        }
        item {
            Text(
                text = "Select all that apply.",
                style = subtitleStyle,
                modifier = GlanceModifier.fillMaxWidth().padding(vertical = 24.dp)
            )
        }
        items(items = answer.optionsStringRes, itemId = { it.toLong() }) { id ->
            SurveyCheckbox(id)
        }
        item {
            SurveyButton(
                text = "Next",
                onClick = actionStartActivity<MainActivity>(
                    actionParametersOf(indexKey.toParametersKey() to 1)
                )
            )
        }
    }
}

@Composable
private fun SurveyQuestionTitle(@StringRes question: Int) {
    Box(
        modifier = GlanceModifier
            .fillMaxWidth()
            .height(72.dp)
            .background(
                day = colorBackgroundNight.copy(alpha = 0.1f),
                night = colorBackgroundDay.copy(alpha = 0.1f)
            )
            .cornerRadius(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(question),
            style = titleStyle,
            modifier = GlanceModifier.fillMaxWidth().padding(8.dp)
        )
    }
}

@Composable
private fun SurveyCheckbox(@StringRes id: Int) {
    val key = "answer-$id"
    val checked = currentState<Preferences>()[booleanPreferencesKey(key)] ?: false
    val onCheckAction = actionRunCallback<SurveyAnswerAction>(
        actionParametersOf(
            questionIndexKey to key,
            ToggleableStateKey to !checked
        )
    )
    Box(GlanceModifier.padding(bottom = 12.dp)) {
        Row(
            modifier = GlanceModifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp)
                .background(
                    imageProvider = ImageProvider(
                        if (checked) {
                            R.drawable.checkbox_outline_checked
                        } else {
                            R.drawable.checkbox_outline
                        }
                    )
                )
                .clickable(onCheckAction),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(stringId = id),
                modifier = GlanceModifier.fillMaxWidth().defaultWeight(),
                style = TextStyle(color = colorTextDayNight)
            )
            CheckBox(
                modifier = GlanceModifier.wrapContentWidth(),
                checked = checked,
                onCheckedChange = onCheckAction,
                colors = CheckBoxColors(
                    checkedColor = ColorProvider(
                        day = colorAccentDay,
                        night = colorAccentNight
                    )
                )
            )
        }
    }
}

@Composable
private fun SurveyButton(text: String, onClick: Action, modifier: GlanceModifier = GlanceModifier) {
    Button(
        text = text,
        modifier = modifier.fillMaxWidth()
            .height(48.dp)
            .background(
                day = colorAccentDay,
                night = colorAccentNight
            )
            .cornerRadius(12.dp),
        onClick = onClick,
        style = TextStyle(
            color = ColorProvider(day = Color.White, night = Color.Black),
            fontWeight = FontWeight.Bold
        )
    )
}

class SurveyAnswerAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        SurveyAppWidget(SurveyRepository.getSurvey()).run {
            updateAppWidgetState<Preferences>(context, glanceId) {
                it.toMutablePreferences().apply {
                    val indexKey = parameters[questionIndexKey]!!
                    this[booleanPreferencesKey(indexKey)] = parameters[ToggleableStateKey] ?: false
                }
            }
            update(context, glanceId)
        }
    }
}

class SurveyNextAction : ActionCallback {
    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val widget = SurveyAppWidget(SurveyRepository.getSurvey())
        widget.updateAppWidgetState<Preferences>(context, glanceId) {
            it.toMutablePreferences().apply {
                this[indexKey] = parameters[indexKey.toParametersKey()] ?: 0
            }
        }
        widget.update(context, glanceId)
    }
}

class SurveyAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SurveyAppWidget(SurveyRepository.getSurvey())
}