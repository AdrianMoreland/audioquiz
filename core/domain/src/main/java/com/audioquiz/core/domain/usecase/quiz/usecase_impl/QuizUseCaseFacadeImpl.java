package com.audioquiz.core.domain.usecase.quiz.usecase_impl;


import com.audioquiz.core.domain.usecase.quiz.usecase.QuizUseCaseFacade;
import com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz.EndQuizUseCaseImpl;
import com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz.GetQuizResultDataImpl;
import com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz.StartQuizUseCaseImpl;
import com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz.SubmitAnswerUseCaseImpl;
import com.audioquiz.core.model.quiz.QuizResult;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


public class QuizUseCaseFacadeImpl implements QuizUseCaseFacade {
    private static final String TAG = "QuizUseCaseFacadeImpl";
    private final StartQuizUseCaseImpl startQuizUseCase;
    private final SubmitAnswerUseCaseImpl submitAnswerUseCase;
    private final EndQuizUseCaseImpl endQuizUseCase;
    private final GetQuizResultDataImpl getQuizResultDataImpl;

    @Inject
    public QuizUseCaseFacadeImpl(StartQuizUseCaseImpl startQuizUseCase,
                                 SubmitAnswerUseCaseImpl submitAnswerUseCase,
                                 EndQuizUseCaseImpl endQuizUseCase,
                                 GetQuizResultDataImpl getQuizResultDataImpl) {
        this.startQuizUseCase = startQuizUseCase;
        this.submitAnswerUseCase = submitAnswerUseCase;
        this.endQuizUseCase = endQuizUseCase;
        this.getQuizResultDataImpl = getQuizResultDataImpl;
    }

    //START
    @Override
    public void startQuiz(String category, int chapter, String quizType) {
        startQuizUseCase.startQuiz(category, chapter, quizType);
    }
    //SUBMIT
    @Override
    public void submitAnswer(int selectedOptionIndex, int selectedOptionIndex2) {
        submitAnswerUseCase.execute(selectedOptionIndex, selectedOptionIndex2);
    }
    //END
    @Override
    public void endQuiz() {
        endQuizUseCase.execute();
    }

/*    @Override
    public LiveData<String> getQuizMedal() {
        return getQuizResultDataImpl.getQuizMedalLiveData();
    }

    @Override
    public LiveData<Boolean> getIsCorrectLiveData() {
        return getQuizResultDataImpl.getIsCorrectLiveData();
    }
    @Override
    public LiveData<String> getCurrentLives() {
        return getQuizResultDataImpl.getCurrentLivesLiveData();
    }
    @Override
    public LiveData<String> getUpdatedUserScore() {
        return getQuizResultDataImpl.getUserScoreLiveData();
    }
    @Override
    public LiveData<Integer> getMaxQuestionsLiveData() {
        return getQuizResultDataImpl.getMaxQuestionsLiveData();
    }
    @Override
    public LiveData<Boolean> getIsQuizEndedLiveData() {
        return getQuizResultDataImpl.getIsQuizEndedLiveData();
    }

    @Override
    public LiveData<Integer> getCorrectOption1() {
        return submitAnswerUseCase.getCorrectOption1();
    }

    @Override
    public LiveData<Integer> getCorrectOption2() {
        return submitAnswerUseCase.getCorrectOption2();
    }

    @Override
    public LiveData<Boolean> getIsLastQuestion() {
        return getQuizResultDataImpl.getIsLastQuestion();
    }*/

    @Override
    public void resetIsQuizEnded() {
        getQuizResultDataImpl.resetIsQuizEnded();
    }

    @Override
    public Single<QuizResult> getQuizResultSingle() {
        return getQuizResultDataImpl.getQuizResultSingle();
    }

    @Override
    public Single<String> getCurrentLivesSingle() {
        return getQuizResultDataImpl.getCurrentLivesSingle();
    }

    @Override
    public Single<String> getUserScoreSingle() {
        return getQuizResultDataImpl.getUserScoreSingle();
    }

    @Override
    public Single<String> getQuizMedalSingle() {
        return getQuizResultDataImpl.getQuizMedalSingle();
    }

    @Override
    public Observable<Boolean> getIsCorrectSingle() {
        return getQuizResultDataImpl.getIsCorrectSingle();
    }

    @Override
    public Single<Integer> getMaxQuestionsSingle() {
        return getQuizResultDataImpl.getMaxQuestionsSingle();
    }

    @Override
    public Observable<Boolean> getIsQuizEndedObservable() {
        return getQuizResultDataImpl.getIsQuizEndedObservable();
    }

    @Override
    public Integer getCorrectOption1() {
        return getQuizResultDataImpl.getCorrectOption1();
    }

    @Override
    public Integer getCorrectOption2() {
        return getQuizResultDataImpl.getCorrectOption2();
    }

    @Override
    public Observable<Boolean> getIsLastQuestion() {
        return getQuizResultDataImpl.getIsLastQuestionObservable();
    }


/*
    @Override
    public Pair<Integer, Boolean> incrementQuestionCount() {
        int questionCount = quizRepository.incrementQuestionCount();
        boolean isQuizEnded = quizRepository.isQuizOver();
        return new Pair<>(questionCount, isQuizEnded);
    }

    @Override
    public void decrementLives() {
        quizRepository.decrementUserLives();
    }*/



}
