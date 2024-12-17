package com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz;


import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.domain.usecase.user.stats.stats.UpdateStatisticsUseCaseImpl;

import javax.inject.Inject;

public class EndQuizUseCaseImpl {
    private final QuizRepository quizRepository;
    private final UpdateStatisticsUseCaseImpl updateStatisticsUseCase;
    private static final String TAG = "EndQuizUseCaseImpl";

    @Inject
    public EndQuizUseCaseImpl(QuizRepository quizRepository,
                              UpdateStatisticsUseCaseImpl updateStatisticsUseCase) {
        this.quizRepository = quizRepository;
        this.updateStatisticsUseCase = updateStatisticsUseCase;
    }

    public void execute() {
        System.out.println("EndQuizUseCase" + "EndQuiz UseCase");
        quizRepository.setIsQuizOver(true);
       //updateStatisticsUseCase.execute(quizRepository.getQuizResult());
    }
}
