package com.audioquiz.core.domain.repository.quiz;

import com.audioquiz.core.model.quiz.Question;
import com.audioquiz.core.model.quiz.QuizResult;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface QuizRepository {
    Single<QuizResult> getQuizResultSingle();

    Observable<Boolean> getIsQuizEndedObservable();

    Observable<Boolean> getIsLastQuestionObservable();

    Observable<Boolean> getIsCorrectObservable();

    QuizResult getQuizResult();

    Boolean getIsCorrect();

    String getCategory();

    int getChapter();

    String getRootNote();

    String getCurrentLives();

    int getQuestionCount();

    int getMaxQuestions();

    Integer getUserScore();

    Boolean getIsQuizEndedLiveData();

    void updateIsCorrect(boolean isCorrect);

    void updateQuizTimeData(Map<String, Object> quizTimeData);

    void updateUserScore();

    void updateQuestionCount();

    void updateLives();

    void updateScorePerCategory(String category);

    void updateScorePerFrequency(boolean isCorrect, Question question, String selectedFrequency);

    String getQuizType();

    void setIsQuizOver(boolean isQuizOver);

    QuizResult getQuizResultLiveData();

    void setIsLastQuestion(boolean isLastQuestion);

    Boolean getIsLastQuestion();


    Integer getCorrectOption2();

    Integer getCorrectOption1();

}

