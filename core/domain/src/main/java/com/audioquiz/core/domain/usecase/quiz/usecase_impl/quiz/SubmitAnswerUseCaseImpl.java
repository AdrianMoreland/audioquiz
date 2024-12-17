package com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz;


import com.audioquiz.core.domain.repository.quiz.FrequenciesDataRepository;
import com.audioquiz.core.domain.repository.quiz.QuestionRepository;
import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.model.quiz.Question;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class SubmitAnswerUseCaseImpl {
    private static final String TAG = "SubmitAnswerUseCaseImpl";
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final EndQuizUseCaseImpl endQuizUseCase;
    private final FrequenciesDataRepository frequenciesDataRepository;
    private final UpdateQuizResultUseCaseImpl updateQuizResultUseCase;
//    private final MutableLiveData<Integer> correctOption1 = new MutableLiveData<>();
//    private final MutableLiveData<Integer> correctOption2 = new MutableLiveData<>();
    private Map<Integer, String> intervalButtonsIndex = new HashMap<>();
    Integer correctOption1 = null;
    Integer correctOption2 = null;

    @Inject
    public SubmitAnswerUseCaseImpl(QuestionRepository questionRepository,
                                   QuizRepository quizRepository,
                                   FrequenciesDataRepository frequenciesDataRepository,
                                   EndQuizUseCaseImpl endQuizUseCase,
                                   UpdateQuizResultUseCaseImpl updateQuizResultUseCase) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.frequenciesDataRepository = frequenciesDataRepository;
        this.endQuizUseCase = endQuizUseCase;
        this.updateQuizResultUseCase = updateQuizResultUseCase;
        setIntervalButtonsIndex();
    }

    private Question getCurrentQuestion() {
        return questionRepository.getCurrentQuestion();
    }

    public Integer getCorrectOption1() {
        return correctOption1;
    }

    public Integer getCorrectOption2() {
        return correctOption2;
    }

    public void execute(int selectedOptionIndex, int selectedOptionIndex2) {
        Question currentQuestion = getCurrentQuestion();
        if (selectedOptionIndex == -1 || currentQuestion == null) {
            System.out.println(TAG + "execute: button1 is -1 or question is null");
            return;
        }
        boolean isCorrect = false;
        String category = currentQuestion.getCategory();
        String selectedFrequency = getSelectedFrequency(currentQuestion, selectedOptionIndex);

        if ("intervals".equals(category)) {
            if (selectedOptionIndex2 != -1) {
                isCorrect = submitIntervalAnswer(selectedOptionIndex, selectedOptionIndex2, currentQuestion);
            }
        } else if ("pitch".equals(category)) {
            isCorrect = submitPitchAnswer(selectedOptionIndex, currentQuestion);
        } else {
            isCorrect = submitTextAnswer(currentQuestion, selectedOptionIndex);
        }

        // Update the quiz result
        updateQuizResultUseCase.execute(isCorrect, currentQuestion, selectedFrequency);
    }

    private String getSelectedFrequency(Question question, int selectedOptionIndex) {
        switch (selectedOptionIndex) {
            case 0:
                return question.getOption1();
            case 1:
                return question.getOption2();
            case 2:
                return question.getOption3();
            case 3:
                return question.getOption4();
            default:
                return "N/A";
        }
    }

    private boolean submitTextAnswer(Question question, int selectedOptionIndex) {
        System.out.println(TAG + "submitTextAnswer - correct answer: " + question.getAnswerNr() + " and selectedOption(s): " + selectedOptionIndex);
        int correctAnswer = question.getAnswerNr();
        boolean isCorrect = selectedOptionIndex == correctAnswer;
        correctOption1 = correctAnswer;
        return isCorrect;
    }

    private boolean submitPitchAnswer(int selectedOptionIndex, Question question) {
        boolean isCorrect = selectedOptionIndex == question.getAnswerNr();
        correctOption1 = question.getAnswerNr();
        System.out.println(TAG + "submitPitchAnswer: - correctAnswer = " + question.getAnswerNr() + " and selectedOption: " + selectedOptionIndex + " = isCorrect: " + isCorrect);
        return isCorrect;
    }

    private boolean submitIntervalAnswer(int selectedOptionIndex1, int selectedOptionIndex2, Question question) {
        correctOption1 = getButtonIndex(question.getOption1());
        correctOption2 =getButtonIndex(question.getOption2());

        boolean isQualityCorrect = false;
        boolean isNumberCorrect = false;
        if (correctOption1 != null && correctOption2 != null) {
            isQualityCorrect = (selectedOptionIndex1 == correctOption1);
            isNumberCorrect = (selectedOptionIndex2 == correctOption2);
            System.out.println(TAG + "submitIntervalAnswer: - interval numbers = " + selectedOptionIndex1 + " and " + correctOption1);
            System.out.println(TAG + "submitIntervalAnswer: - interval quality = " + selectedOptionIndex2 + " and " + correctOption2);
        }

        return isNumberCorrect && isQualityCorrect;
    }

    private int getButtonIndex(String option) {
        return intervalButtonsIndex.entrySet().stream()
                .filter(entry -> entry.getValue().equals(option))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(-1);
    }

    private void setIntervalButtonsIndex() {
        this.intervalButtonsIndex = frequenciesDataRepository.getIntervalButtonsIndex();
    }

    private void checkIsLastQuestion() {
        boolean isLastQuestion = quizRepository.getQuestionCount() == quizRepository.getMaxQuestions();
        System.out.println(TAG + "Checking last question. count:" + quizRepository.getQuestionCount() +
                " and max questions: " + quizRepository.getMaxQuestions());
        quizRepository.setIsLastQuestion(isLastQuestion);
    }

    private int getCorrectAnswerFromDatabase(int questionId) {
        return questionRepository.getCorrectAnswerNr(questionId);
    }

/*       private void updateQuizResult(boolean isCorrect, QuestionDto question) {
        updateIsCorrect(isCorrect);
        updateQuestionCount();
        if (isCorrect) {
            updateUserScore();
            updateScorePerCategory(question.getCategory());
        } else {
            updateLives();
        }
        if ("pitch".equals(question.getCategory())) {
            updateScorePerFrequency(isCorrect, question);
        }
        checkIsLastQuestion();
    }

     private void updateIsCorrect(boolean isCorrect) {
        quizRepository.updateIsCorrect(isCorrect);
    }

    public void updateQuestionCount() {
        quizRepository.updateQuestionCount();
    }

    private void updateUserScore() {
        quizRepository.updateUserScore();
    }

    private void updateScorePerCategory(String category) {
        quizRepository.updateScorePerCategory(category);
    }

    private void updateScorePerFrequency(boolean isCorrect, QuestionDto question) {
        quizRepository.updateScorePerFrequency(isCorrect, question);
    }

    private void updateLives() {
        quizRepository.updateLives();
    }

        private void checkIsLastQuestion() {
        System.out.println(TAG + "Checking last question. count:" + quizRepository.getQuestionCount() +
                " and max questions: " + quizRepository.getMaxQuestions());
        if (quizRepository.getQuestionCount() == quizRepository.getMaxQuestions()) {
            System.out.println(TAG + "Last question submitted");
            quizRepository.setIsLastQuestion();
        }
    }


*/

}
