package com.audioquiz.core.domain.usecase.quiz.question;


import com.audioquiz.core.model.quiz.Question;

import java.io.InputStream;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface QuestionUseCaseFacade {

    Single <Question> getQuestion();

    void setQuizSelection(String category, int chapter, String quizType);

    void preloadQuestionsUseCase(InputStream inputStream, Runnable onCompletion);

    void playPitchFrequency(int finalCorrectFrequency);

    void playIntervalFrequency(double rootNote, double secondNote);

    void playFrequency(Question question);

    void insertAllQuestions(List<Question> questions);
}
