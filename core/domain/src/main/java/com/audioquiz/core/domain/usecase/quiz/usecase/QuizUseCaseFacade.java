package com.audioquiz.core.domain.usecase.quiz.usecase;


import com.audioquiz.core.model.quiz.QuizResult;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface QuizUseCaseFacade {
    void startQuiz(String category, int chapter, String quizType);
    void submitAnswer(int selectedOptionIndex, int selectedOptionIndex2);
    void endQuiz();
    void resetIsQuizEnded();

    Single<QuizResult> getQuizResultSingle();
    Single<String> getCurrentLivesSingle();
    Single<String> getUserScoreSingle();
    Single<String> getQuizMedalSingle();
    Observable<Boolean> getIsCorrectSingle();
    Single<Integer> getMaxQuestionsSingle();
    Observable<Boolean> getIsQuizEndedObservable();

    Integer getCorrectOption1();
    Integer getCorrectOption2();

    Observable<Boolean> getIsLastQuestion();




/*    Pair<Integer, Boolean> incrementQuestionCount();
     void updateUserScore();
   void decrementLives();
    void AddQuizResultUseCase(String category, int chapter, String quizType);*/
}
