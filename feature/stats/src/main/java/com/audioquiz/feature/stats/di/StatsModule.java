package com.audioquiz.feature.stats.di;


import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class StatsModule {
      /*  @Provides
        @Singleton
        public StatsRepository provideStatsRepository(StatsDataSource statsDataSource,
                                                      FrequencyStatsDao frequencyStatsDao) {
            return new StatsRepository_Impl(statsDataSource, frequencyStatsDao);
        }


        @Provides
        @Singleton
        public QuestionUseCaseFacade provideQuestionUseCases(QuestionRepository questionRepository,
                                                             LoadQuestionUseCaseImpl loadQuestionUseCase,
                                                             PreloadQuestionsUseCaseImpl preloadQuestionsUseCase,
                                                             PlayFrequencyUseCaseImpl playFrequencyUseCase) {
            return new QuestionUseCaseFacadeImpl(questionRepository, loadQuestionUseCase, preloadQuestionsUseCase, playFrequencyUseCase);
        }

        @Provides
        @Singleton
        public QuizUseCaseFacade provideQuizUseCaseFacade(StartQuizUseCaseImpl startQuizUseCase,
                                                          SubmitAnswerUseCaseImpl submitAnswerUseCase,
                                                          EndQuizUseCaseImpl endQuizUseCase,
                                                          GetQuizResultDataImpl getQuizResultDataImpl) {
            return new QuizUseCaseFacadeImpl(startQuizUseCase,
                    submitAnswerUseCase,
                    endQuizUseCase,
                    getQuizResultDataImpl);
        }*/
    }

