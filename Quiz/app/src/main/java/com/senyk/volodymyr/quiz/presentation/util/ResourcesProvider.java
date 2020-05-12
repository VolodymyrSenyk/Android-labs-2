package com.senyk.volodymyr.quiz.presentation.util;

import android.content.Context;
import android.content.res.Resources;

import com.senyk.volodymyr.quiz.R;
import com.senyk.volodymyr.quiz.domain.entity.Score;

import javax.inject.Inject;

public class ResourcesProvider {

    private final Resources resources;

    @Inject
    ResourcesProvider(Context context) {
        this.resources = context.getResources();
    }

    public String getErrorMessage() {
        return resources.getString(R.string.error_message);
    }

    public String getExitMessage() {
        return resources.getString(R.string.exit_message);
    }

    public String getEndGameMessage(final Score score) {
        if (score.getCorrectAnswers() == 0) {
            return resources.getString(R.string.user_bad_score_message);
        } else {
            int correctAnswers = score.getCorrectAnswers();
            String correctAnswersCount = resources.getQuantityString(R.plurals.poets, correctAnswers, correctAnswers);
            return resources.getString(R.string.user_score_message, correctAnswersCount, score.getNumberOfQuestions());
        }
    }
}
