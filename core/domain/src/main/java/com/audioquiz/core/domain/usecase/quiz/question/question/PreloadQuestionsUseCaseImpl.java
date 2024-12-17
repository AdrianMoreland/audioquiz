package com.audioquiz.core.domain.usecase.quiz.question.question;

import com.audioquiz.core.domain.repository.quiz.QuestionRepository;

import java.io.InputStream;

import javax.inject.Inject;


public class PreloadQuestionsUseCaseImpl {
    private final QuestionRepository questionRepository;

    @Inject
    public PreloadQuestionsUseCaseImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void execute (InputStream inputStream, Runnable onCompletion) {
        questionRepository.preloadQuestionsFromJsonOnLaunch(inputStream, onCompletion);
    }

}
