package com.adrian.usecase.stats.usecase_impl.stats;

import com.adrian.model.quiz.QuizResult;
import com.adrian.model.stats.Last7DaysScores;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import javax.inject.Inject;


public class UpdateDailyScoresUseCaseImpl {
    private static final String TAG = "UpdateDailyScoresUseCaseImpl";

    @Inject
    public UpdateDailyScoresUseCaseImpl() {

    }

    public Last7DaysScores execute(QuizResult quizResult, Last7DaysScores last7DaysScores) {
        Map<String, Integer> dailyScores = last7DaysScores.getDailyScores();
        System.out.println("UpdateDailyScoresUsecase" + "Updating Scores for Last 7 days");
        System.out.println("UpdateDailyScoresUsecase" + "BEFORE: " + dailyScores);

        int quizScore = quizResult.getScore();
        Date lastQuizDate = (Date) quizResult.getQuizTimeData().get("quizDate");

        int totalLast7Days = updateTotalScore(dailyScores);

        String dateKey =lastQuizDate.toString();
        Integer currentScore = dailyScores.get(dateKey);
        if (currentScore == null) {
            currentScore = 0;
        }
        currentScore += quizScore;
        dailyScores.put(dateKey, currentScore);
        System.out.println("UpdateDailyScoresUsecase" + "AFTER: " + dailyScores);

        // Keep only the last 7 days
        if (dailyScores.size() > 7) {
            String oldestDate = Collections.min(dailyScores.keySet());
            dailyScores.remove(oldestDate);
        }
        return last7DaysScores;
    }


    private int updateTotalScore(Map<String, Integer> dailyScores) {
        return dailyScores.values().stream().mapToInt(Integer::intValue).sum();
    }
}
