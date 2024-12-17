package com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz;


import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.domain.repository.user.StatsRepository;
import com.audioquiz.core.model.quiz.QuizResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class StartQuizUseCaseImpl {
    private static final String TAG = "StartQuizUseCaseImpl";
    private final QuizRepository quizRepository;
    private final StatsRepository statsRepository;

    @Inject
    public StartQuizUseCaseImpl(QuizRepository quizRepository,
                                StatsRepository statsRepository) {
        this.quizRepository = quizRepository;
        this.statsRepository = statsRepository;
    }

    public void startQuiz(String category, int chapter, String quizType) {
        QuizResult quizResult = quizRepository.getQuizResult();

        String rootNote = setupRootNote(quizType);
        int maxQuestions = setupMaxQuestions(chapter);
        int questionCount = 0;
        int lives = setupUserLives();
        Map<String, Integer> scorePerFrequency = new HashMap<>();
        Map<String, Integer> scorePerCategory = new HashMap<>();
        Date quizDate = new Date();
        long startTime = System.currentTimeMillis(); // Start the quiz timer

        quizRepository.setIsLastQuestion(false);

        System.out.println("StartQuizUsecase" + "Max questions set: " + maxQuestions);

        quizResult.setCategory(category);
        quizResult.setChapter(chapter);
        quizResult.setQuizType(quizType);
        quizResult.setRootNote(rootNote);
        quizResult.setMaxQuestions(maxQuestions);
        quizResult.setQuestionCounter(questionCount);
        quizResult.setLives(lives);
        quizResult.setScorePerFrequency(scorePerFrequency);
        quizResult.setScorePerCategory(scorePerCategory);
        quizResult.getQuizTimeData().put("quizDate", quizDate);
        quizResult.getQuizTimeData().put("startTime", startTime);
    }

    private Integer setupUserLives() {
        StatsRepository.UserLivesObserver observer = new StatsRepository.UserLivesObserver() {
            @Override
            public void onUserLivesChanged(int userLives) {
                System.out.println("StartQuizUsecase" + "User lives changed: " + userLives);
            }

            @Override
            public void onError(Exception e) {

            }
            // Implement the methods required by UserLivesObserver
        };
        return statsRepository.getUserLives(observer);
    }

    private int setupMaxQuestions(int chapter) {
        // TEST!!
        return 3;

/*        if (chapter == 4) {
            return 15;
        } else {
            return 10;
        }
*/
    }

    private String setupRootNote(String quizType) {
        if (quizType.equals("lesson") || quizType.equals("play")) {
            return quizType;
        } else {
            return "N/A";
        }
    }
}
