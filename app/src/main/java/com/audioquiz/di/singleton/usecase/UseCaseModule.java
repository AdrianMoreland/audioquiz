package com.audioquiz.di.singleton.usecase;

import com.adrian.usecase.question.usecase.FrequenciesDataUseCase;
import com.adrian.usecase.question.usecase.QuestionUseCaseFacade;
import com.adrian.usecase.question.usecase_impl.FrequenciesDataUseCaseImpl;
import com.adrian.usecase.question.usecase_impl.QuestionUseCaseFacadeImpl;
import com.adrian.usecase.quiz.usecase.QuizUseCaseFacade;
import com.adrian.usecase.quiz.usecase_impl.QuizUseCaseFacadeImpl;
import com.adrian.usecase.rank.usecase.RankUseCaseFacade;
import com.adrian.usecase.rank.usecase_impl.RankUseCaseFacadeImpl;
import com.adrian.usecase.stats.usecase.StatisticsUseCaseFacade;
import com.adrian.usecase.stats.usecase_impl.StatisticsUseCaseFacadeImpl;
import com.adrian.usecase.auth.UserAuthUseCaseFacade;
import com.adrian.usecase.user.usecase.UserProfileUseCaseFacade;
import com.adrian.usecase.user.usecase_impl.UserAuthUseCaseFacadeImpl;
import com.adrian.usecase.user.usecase_impl.UserProfileUseCaseFacadeImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class UseCaseModule {

    @Provides
    static UserAuthUseCaseFacade provideUserAuthUseCaseFacade(UserAuthUseCaseFacadeImpl userAuthUseCaseFacadeImpl) {
        return userAuthUseCaseFacadeImpl;
    }

    @Provides
    static QuestionUseCaseFacade provideQuestionUseCaseFacade(QuestionUseCaseFacadeImpl questionUseCaseFacadeImpl) {
        return questionUseCaseFacadeImpl;
    }

    @Provides
    static FrequenciesDataUseCase provideFrequenciesDataUseCase(FrequenciesDataUseCaseImpl frequenciesDataUseCaseImpl) {
        return frequenciesDataUseCaseImpl;
    }

    @Provides
    static QuizUseCaseFacade provideQuizUseCaseFacade(QuizUseCaseFacadeImpl quizUseCaseFacadeImpl) {
        return quizUseCaseFacadeImpl;
    }

    @Provides
    static UserProfileUseCaseFacade provideUserProfileUseCaseFacade(UserProfileUseCaseFacadeImpl userProfileUseCaseFacadeImpl) {
        return userProfileUseCaseFacadeImpl;
    }

    @Provides
    static StatisticsUseCaseFacade provideStatisticsUseCaseFacade(StatisticsUseCaseFacadeImpl statisticsUseCaseFacade) {
        return statisticsUseCaseFacade;
    }

    @Provides
    static RankUseCaseFacade provideRankUseCaseFacade(RankUseCaseFacadeImpl rankUseCaseFacade) {
        return rankUseCaseFacade;
    }
}