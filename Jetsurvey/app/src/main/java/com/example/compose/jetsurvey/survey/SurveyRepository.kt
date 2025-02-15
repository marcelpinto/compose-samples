/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetsurvey.survey

import android.os.Build
import com.example.compose.jetsurvey.R
import com.example.compose.jetsurvey.survey.PossibleAnswer.*
import com.example.compose.jetsurvey.survey.SurveyActionType.PICK_DATE
import com.example.compose.jetsurvey.survey.SurveyActionType.TAKE_PHOTO

// Static data of questions
private val jetpackQuestions = mutableListOf(
    Question(
        id = 1,
        questionText = R.string.in_my_free_time,
        answer = MultipleChoice(
            optionsStringRes = listOf(
                R.string.read,
                R.string.work_out,
                R.string.draw,
                R.string.play_games,
                R.string.dance,
                R.string.watch_movies
            )
        ),
        description = R.string.select_all
    ),
    Question(
        id = 2,
        questionText = R.string.pick_superhero,
        answer = PossibleAnswer.SingleChoiceIcon(
            optionsStringIconRes = listOf(
                Pair(R.drawable.spark, R.string.spark),
                Pair(R.drawable.lenz, R.string.lenz),
                Pair(R.drawable.bug_of_chaos, R.string.bugchaos),
                Pair(R.drawable.frag, R.string.frag)
            )
        ),
        description = R.string.select_one
    ),
    Question(
        id = 7,
        questionText = R.string.favourite_movie,
        answer = SingleChoice(
            listOf(
                R.string.star_trek,
                R.string.social_network,
                R.string.back_to_future,
                R.string.outbreak
            )
        ),
        description = R.string.select_one
    ),
).toList()

private val jetpackSurvey = Survey(
    title = R.string.which_jetpack_library,
    questions = jetpackQuestions
)

object SurveyRepository {

    fun getSurvey() = jetpackSurvey

    @Suppress("UNUSED_PARAMETER")
    fun getSurveyResult(answers: List<Answer<*>>): SurveyResult {
        return SurveyResult(
            library = "Compose",
            result = R.string.survey_result,
            description = R.string.survey_result_description
        )
    }
}
