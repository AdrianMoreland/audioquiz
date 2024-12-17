package com.audioquiz.core.domain.usecase.quiz.question;


import com.audioquiz.core.domain.repository.quiz.QuestionRepository;
import com.audioquiz.core.domain.usecase.quiz.question.question.InsertQuestionsUseCaseImpl;
import com.audioquiz.core.domain.usecase.quiz.question.question.LoadQuestionUseCaseImpl;
import com.audioquiz.core.domain.usecase.quiz.question.question.PlayFrequencyUseCaseImpl;
import com.audioquiz.core.domain.usecase.quiz.question.question.PreloadQuestionsUseCaseImpl;
import com.audioquiz.core.model.quiz.Question;

import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;


public class QuestionUseCaseFacadeImpl implements QuestionUseCaseFacade {
    private final QuestionRepository questionRepository;
    private final LoadQuestionUseCaseImpl loadQuestionUseCaseImpl;
    private final PreloadQuestionsUseCaseImpl preloadQuestionsUseCase;
    private final PlayFrequencyUseCaseImpl playFrequencyUseCase;
    private final InsertQuestionsUseCaseImpl insertQuestionsUseCase;


    @Inject
    public QuestionUseCaseFacadeImpl(QuestionRepository questionRepository,
                                     LoadQuestionUseCaseImpl loadQuestionUseCaseImpl,
                                     PreloadQuestionsUseCaseImpl preloadQuestionsUseCase,
                                     PlayFrequencyUseCaseImpl playFrequencyUseCase,
                                     InsertQuestionsUseCaseImpl insertQuestionsUseCase) {
        this.questionRepository = questionRepository;
        this.loadQuestionUseCaseImpl = loadQuestionUseCaseImpl;
        this.preloadQuestionsUseCase = preloadQuestionsUseCase;
        this.playFrequencyUseCase = playFrequencyUseCase;
        this.insertQuestionsUseCase = insertQuestionsUseCase;
    }

    @Override
    public void preloadQuestionsUseCase(InputStream inputStream, Runnable onCompletion) {
        preloadQuestionsUseCase.execute(inputStream, onCompletion);
    }

    @Override
    public void playPitchFrequency(int frequency) {
        playFrequencyUseCase.playPitchFrequency(frequency);
    }

    @Override
    public void playIntervalFrequency(double rootNote, double secondNote) {
        playFrequencyUseCase.playIntervalFrequency(rootNote, secondNote);
    }

    @Override
    public void playFrequency(Question question) {
        playFrequencyUseCase.playFrequency(question);
    }

    @Override
    public Single<Question> getQuestion() {
        return loadQuestionUseCaseImpl.execute();
    }

    @Override
    public void setQuizSelection(String category, int chapter, String quizType) {
        questionRepository.setQuizSelection(category, chapter, quizType);
    }

    @Override
    public void insertAllQuestions(List<Question> questions) {
        insertQuestionsUseCase.execute(questions);
    }
}
