package com.audioquiz.core.domain.usecase.quiz.question.question;

import com.audioquiz.core.domain.repository.quiz.QuestionRepository;
import com.audioquiz.core.model.quiz.Question;

import java.util.List;

import javax.inject.Inject;


public class InsertQuestionsUseCaseImpl {
    private final QuestionRepository questionRepository;

    @Inject
    public InsertQuestionsUseCaseImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void execute (List<Question> questions) {
        questionRepository.insertQuestions(questions);
    }

}
