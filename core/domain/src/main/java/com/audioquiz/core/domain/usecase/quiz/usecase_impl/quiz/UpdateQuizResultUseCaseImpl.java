package com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz;


import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.model.quiz.Question;

import javax.inject.Inject;

public class UpdateQuizResultUseCaseImpl {
    private static final String TAG = "UpdateQuizResultUseCaseImpl";
    private final QuizRepository quizRepository;

    @Inject
    public UpdateQuizResultUseCaseImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public void execute(boolean isCorrect, Question currentQuestion, String selectedFrequency) {
        updateIsCorrect(isCorrect);
        updateQuestionCount();
        if (isCorrect) {
            updateUserScore();
            updateScorePerCategory(currentQuestion.getCategory());
        } else {
            updateLives();
        }
        if ("pitch".equals(currentQuestion.getCategory())) {
            updateScorePerFrequency(isCorrect, currentQuestion, selectedFrequency);
        }
     //   checkIsLastQuestion();
    }

    private void updateIsCorrect(boolean isCorrect) {
        quizRepository.updateIsCorrect(isCorrect);
    }

    public void updateQuestionCount() {
        quizRepository.updateQuestionCount();
        System.out.println(TAG + "QuestionDto count updated: " + quizRepository.getQuestionCount());
        checkIsLastQuestion();
    }

    private void checkIsLastQuestion() {
        // Check if it's the last question and update LiveData
        boolean isLastQuestion = quizRepository.getQuestionCount() == quizRepository.getMaxQuestions() -1;
        System.out.println(TAG + "Checking last question. count:" + quizRepository.getQuestionCount() +
                " and max questions: " + quizRepository.getMaxQuestions());
        quizRepository.setIsLastQuestion(isLastQuestion);
    }

    private void updateUserScore() {
        quizRepository.updateUserScore();
    }

    private void updateScorePerCategory(String category) {
        quizRepository.updateScorePerCategory(category);
    }

    private void updateScorePerFrequency(boolean isCorrect, Question question, String selectedFrequency) {
        quizRepository.updateScorePerFrequency(isCorrect, question, selectedFrequency);
    }

    private void updateLives() {
        quizRepository.updateLives();
    }


}
