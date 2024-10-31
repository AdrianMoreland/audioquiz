package com.audioquiz.core.domain.usecase.user.stats.stats;

import com.audioquiz.core.domain.repository.user.StatsRepository;
import com.audioquiz.core.model.quiz.QuizResult;
import com.audioquiz.core.model.user.stats.GeneralStats;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class UpdateGeneralStatsUseCaseImpl {
    private final StatsRepository statsRepository;

    @Inject
    public UpdateGeneralStatsUseCaseImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    public GeneralStats execute(GeneralStats generalStats, QuizResult quizResult) {
        // QUIZ_RESULT_DATA
        int quizScore = quizResult.getScore();
       int quizQuestions = quizResult.getMaxQuestions();
        Date newLastQuizDate = (Date) quizResult.getQuizTimeData().get("quizDate");
        // GENERAL_STATS_DATA
        int numberOfQuizzes = incrementNumberOfQuizzes(generalStats.getNumberOfQuizzes());
        int totalQuestions = incrementNumberOfQuestions(generalStats.getNumberOfQuestions(), quizQuestions);
        int currentTotalScore = incrementTotalScore(generalStats.getTotalScore(), quizScore);
        double averageScore = updateAverageScore(currentTotalScore, totalQuestions);
        int level = calculateUserLevel(currentTotalScore);
        int newCurrentStreak = calculateCurrentStreak(generalStats.getCurrentStreak(), generalStats.getLastQuizDate(), newLastQuizDate);
        int longestStreak = calculateLongestDayStreak(newCurrentStreak, generalStats.getLongestStreak()); // Calculate the longest streak


        generalStats = new GeneralStats.Builder()
                .setUserLevel(level)
                .setNumberOfLives(generalStats.getNumberOfLives())
                .setNumberOfQuizzes(numberOfQuizzes)
                .setNumberOfQuestions(totalQuestions)
                .setTotalScore(currentTotalScore)
                .setAverageScore(averageScore)
                .setCurrentStreak(newCurrentStreak)
                .setLongestStreak(longestStreak)
                .setLastQuizDate(newLastQuizDate)
                .build();

        return generalStats;
    }

    private int incrementNumberOfQuestions(int numberOfQuestions, int increment) {
        return numberOfQuestions + increment;
    }

    private int incrementNumberOfQuizzes(int numberOfQuizzes) {
        return numberOfQuizzes + 1;
    }

    private int incrementTotalScore(int totalScore, int increment) {
        return totalScore + increment;
    }

    private double updateAverageScore(int totalScore, int totalQuestions) {
        double averageScore;
        System.out.println("UpdateGeneralStatsUseCaseImpl" + "TotalQuestions: " + totalQuestions + " TotalScore: " + totalScore);

        DecimalFormat df = new DecimalFormat("#.##"); // Format to 2 decimal places
        if (totalQuestions > 0) {
            // Perform the division first, then multiply by 100
            double rawAverage = ((double) totalScore / totalQuestions) * 100; // Convert to percentage
            averageScore = Double.parseDouble(df.format(rawAverage)); // Format to 2 decimal places
        } else {
            averageScore = 0.0;
        }
        return averageScore;
    }


    private int calculateUserLevel(int totalScore) {
        return totalScore / 100 + 1;
    }

    public int calculateCurrentStreak(int currentDayStreak, Date oldLastQuizDate, Date newLastQuizDate) {
        Date quizDate = new Date();
        if (newLastQuizDate != null) {
            Calendar newLastQuizCalendar = Calendar.getInstance();
            newLastQuizCalendar.setTime(newLastQuizDate);
            Calendar todayCalendar = Calendar.getInstance();

            if (oldLastQuizDate != null) {
                Calendar oldLastQuizCalendar = Calendar.getInstance();
                oldLastQuizCalendar.setTime(oldLastQuizDate);

                if (isSameDay(newLastQuizCalendar.getTime(), todayCalendar.getTime()) &&
                        !isSameDay(oldLastQuizCalendar.getTime(), todayCalendar.getTime())) {
                    currentDayStreak++;
                }
            } else if (isSameDay(newLastQuizCalendar.getTime(), todayCalendar.getTime())) {
                currentDayStreak++;
            }
        }

        return currentDayStreak;
    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    private int calculateLongestDayStreak(int currentStreak, int longestStreak) {
        return Math.max(currentStreak, longestStreak);
    }


    public Map<String, Integer> calculateStreaks(GeneralStats generalStats, Date newLastQuizDate) {
        Date oldLastQuizDate = generalStats.getLastQuizDate(); // Get the old last quiz date

        int currentDayStreak = generalStats.getCurrentStreak(); // Get the current streak
        int currentLongestStreak = generalStats.getLongestStreak(); // Get the longest streak

        int newCurrentStreak = calculateCurrentStreak(currentDayStreak, oldLastQuizDate, newLastQuizDate); // Calculate the current streak
        int newLongestStreak = calculateLongestDayStreak(currentDayStreak, currentLongestStreak); // Calculate the longest streak

        Map<String, Integer> streaks = new HashMap<>();
        streaks.put("currentDayStreak", newCurrentStreak);
        streaks.put("longestDayStreak", newLongestStreak);

        return streaks; // Return the updated streaks map
    }
}
