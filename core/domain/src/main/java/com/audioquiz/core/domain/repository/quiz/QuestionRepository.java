package com.audioquiz.core.domain.repository.quiz;

import com.audioquiz.core.model.quiz.Question;

import java.io.InputStream;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface QuestionRepository {
    void preloadQuestionsFromJsonOnLaunch(InputStream inputStream, Runnable onInputStreamClosed);

    Single<Question> getQuestionSingle(String category, int chapter);

    int getCorrectAnswerNr(int questionId);
    void setRootNote(String rootNote);
    String getRootNote();

    // LOADING_QUIZ_QUESTIONS_TO_UI
    // --------------------------------------------------------------------------------------------
    Question getQuestion(String category, int chapter);

    void setQuizSelection(String category, int chapter, String quizType);

    Question getCurrentQuestion();

    void init();

    void insertQuestions(List<Question> questions);
}
