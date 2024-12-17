package com.audioquiz.api.datasources.quiz_result;


import com.audioquiz.core.model.quiz.QuizResult;

import io.reactivex.rxjava3.core.Single;

public interface QuizLocal {
    Single<QuizResult> getQuizResult();
}
