package com.audioquiz.di.singleton.usecase;


import com.audioquiz.core.domain.usecase.auth.UserAuthUseCaseFacade;
import com.audioquiz.core.domain.usecase.auth.UserAuthUseCaseFacadeImpl;
import com.audioquiz.core.domain.usecase.quiz.question.FrequenciesDataUseCase;
import com.audioquiz.core.domain.usecase.quiz.question.FrequenciesDataUseCaseImpl;
import com.audioquiz.core.domain.usecase.quiz.question.QuestionUseCaseFacade;
import com.audioquiz.core.domain.usecase.quiz.question.QuestionUseCaseFacadeImpl;
import com.audioquiz.core.domain.usecase.quiz.usecase.QuizUseCaseFacade;
import com.audioquiz.core.domain.usecase.quiz.usecase_impl.QuizUseCaseFacadeImpl;
import com.audioquiz.core.domain.usecase.rank.RankUseCaseFacade;
import com.audioquiz.core.domain.usecase.rank.RankUseCaseFacadeImpl;
import com.audioquiz.core.domain.usecase.user.SyncUserDataUseCase;
import com.audioquiz.core.domain.usecase.user.SyncUserDataUseCaseImpl;
import com.audioquiz.core.domain.usecase.user.profile.UserProfileUseCaseFacade;
import com.audioquiz.core.domain.usecase.user.profile.UserProfileUseCaseFacadeImpl;
import com.audioquiz.core.domain.usecase.user.stats.StatisticsUseCaseFacade;
import com.audioquiz.core.domain.usecase.user.stats.StatisticsUseCaseFacadeImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class UseCaseModule {

    @Binds
    public abstract SyncUserDataUseCase bindSyncUserDataUseCase(SyncUserDataUseCaseImpl impl);

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