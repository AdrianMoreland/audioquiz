package com.adrian.usecase.stats.usecase_impl.stats;


import com.adrian.domain.user.StatsRepository;
import com.adrian.model.stats.CategoryStats;
import com.adrian.model.stats.FrequencyStats;
import com.adrian.model.stats.GeneralStats;
import com.adrian.model.stats.Last7DaysScores;
import com.adrian.model.stats.UserStats;
import com.adrian.model.quiz.QuizResult;

import java.util.Date;

import javax.inject.Inject;


public class UpdateStatisticsUseCaseImpl {
    private final StatsRepository statsRepository;
    private final UpdateGeneralStatsUseCaseImpl updateGeneralStatsUseCase;
    private final UpdateCategoryStatsUseCaseImpl updateCategoryStatsUseCase;
    private final UpdatePitchStatsUseCaseImpl updatePitchStatsUseCase;
    private final UpdateDailyScoresUseCaseImpl updateDailyScoresUseCase;

    @Inject
    public UpdateStatisticsUseCaseImpl(StatsRepository statsRepository,
                                       UpdateGeneralStatsUseCaseImpl updateGeneralStatsUseCase,
                                       UpdateCategoryStatsUseCaseImpl updateCategoryStatsUseCase,
                                       UpdatePitchStatsUseCaseImpl updatePitchStatsUseCase,
                                       UpdateDailyScoresUseCaseImpl updateDailyScoresUseCase) {
        this.statsRepository = statsRepository;
        this.updateGeneralStatsUseCase = updateGeneralStatsUseCase;
        this.updateCategoryStatsUseCase = updateCategoryStatsUseCase;
        this.updatePitchStatsUseCase = updatePitchStatsUseCase;
        this.updateDailyScoresUseCase = updateDailyScoresUseCase;
    }

    public void execute(QuizResult quizResult) {
        UserStats userStatistics  = statsRepository.getUserStatsSingle().blockingGet();

        Date quizDate = getQuizDate(quizResult);

        GeneralStats generalStats = updateGeneralStats(quizResult, userStatistics.getGeneralStats());
        CategoryStats categoryStats = updateCategoryStats(quizResult, userStatistics.getCategoryStats());
        FrequencyStats frequencyStats = userStatistics.getFrequencyStats();
        Last7DaysScores last7DaysScores = updateLast7DaysScores(quizResult, userStatistics.getLast7DaysScores());

        if (quizResult.getCategory().equals("pitch")) {
            frequencyStats = updatePitchStats(quizResult, userStatistics.getFrequencyStats());
        }

        userStatistics.setLastUpdated(new Date());
        userStatistics.setGeneralStats(generalStats);
        userStatistics.setCategoryStats(categoryStats);
        userStatistics.setLast7DaysScores(last7DaysScores);
        userStatistics.setFrequencyStats(frequencyStats);

        // Update the general stats in the cache
        statsRepository.saveUserStats(userStatistics);
    }

    private Last7DaysScores updateLast7DaysScores(QuizResult quizResult, Last7DaysScores last7DaysScores) {
        return updateDailyScoresUseCase.execute(quizResult, last7DaysScores);
    }

    private Date getQuizDate(QuizResult quizResult) {
        Object quizDateObj = quizResult.getQuizTimeData().get("quizDate");
        return new Date();
    }

    private GeneralStats updateGeneralStats(QuizResult quizResult, GeneralStats generalStats) {
        return updateGeneralStatsUseCase.execute(generalStats, quizResult);
    }

    private FrequencyStats updatePitchStats(QuizResult quizResult, FrequencyStats frequencyStats) {
        return updatePitchStatsUseCase.execute(quizResult, frequencyStats);
    }

    private CategoryStats updateCategoryStats (QuizResult quizResult, CategoryStats categoryStats) {
        return updateCategoryStatsUseCase.execute(quizResult, categoryStats);
    }


    /*private com.example.model.domain.CategoryStats updateCategoryStats (com.example.model.domain.QuizResult quizResult, com.example.model.domain.CategoryStats categoryStats) {
        String quizType = quizResult.getQuizType();
        String category = quizResult.getCategory();

        if (quizType.equalsIgnoreCase("LESSON")) {
            updateLessonProgression(categoryStats, quizResult);
        } else if (quizType.equalsIgnoreCase("COMPETITIVE")) {
            updateCompetitiveProgression(categoryStats, quizResult);
        } else if (category.equalsIgnoreCase("pitch")) {
            updateCompetitiveProgression(categoryStats, quizResult);
        } else {
            Log.d(TAG, "An error has occurred: " + quizResult.getCategory() + quizResult.getChapter());
        }
        return categoryStats;
    }

    private void updateLessonProgression(com.example.model.domain.CategoryStats categoryStats, com.example.model.domain.QuizResult quizResult) {
        int score = quizResult.getScore();
        String category = quizResult.getCategory();
        int chapter = quizResult.getChapter();
        com.example.model.domain.CategoryStatsData categoryStatsData = categoryStats.getCategoryStatsData(category);

        int currentChapter = categoryStatsData != null ? categoryStatsData.getCurrentChapter() : 1;
        int numberOfQuizzes = categoryStatsData != null ? categoryStatsData.getNumberOfQuizzes() : 1;
        int correctAnswersLearn = categoryStatsData != null ? categoryStatsData.getCorrectAnswersLearn() : 1;
        int totalQuestionsLearn = categoryStatsData != null ? categoryStatsData.getTotalQuestionsLearn() : 1;

        if (chapter < 4) {
            totalQuestionsLearn += 7;
            if (quizResult.getScore() > 2) {
                currentChapter += 1;
            } else {
                Log.d(TAG, "Score is less than passing score");
            }
        } else if (chapter == 4) {
            totalQuestionsLearn += 15;
            if (score > 11) {
                currentChapter += 1;
            } else {
                Log.d(TAG, "Score is less than passing score");
            }
        } else {
            Log.d(TAG, "Chapter is greater than 4");
        }

        com.example.model.domain.CategoryStatsData updatedProgressionData = new com.example.model.domain.CategoryStatsData.Builder()
                .setCurrentChapter(currentChapter)
                .setNumberOfQuizzes(numberOfQuizzes)
                .setTotalQuestionsLearn(totalQuestionsLearn)
                .setCorrectAnswersLearn(correctAnswersLearn)
                .build();

        categoryStats.setCategoryStatsData(category, updatedProgressionData);
    }

    private void updateCompetitiveProgression(com.example.model.domain.CategoryStats categoryStats, com.example.model.domain.QuizResult quizResult) {
        Map<String, Integer> scorePerCategory = quizResult.getScorePerCategory();
        Log.d(TAG, "updateCompetitiveProgression: " + scorePerCategory);
        for (Map.Entry<String, com.example.model.domain.CategoryStatsData> entry : categoryStats.getAllCategoryStatsData().entrySet()) {
            String category = entry.getKey();
            com.example.model.domain.CategoryStatsData statsData = entry.getValue();

            if (scorePerCategory.containsKey(category)) {
                Integer scoreInteger = scorePerCategory.get(category);
                int score = scoreInteger != null ? scoreInteger : 0;
                statsData.setCorrectAnswersCompetitive(statsData.getCorrectAnswersCompetitive() + score);
                statsData.setTotalQuestionsCompetitive(statsData.getTotalQuestionsCompetitive() + 10);
            }
        }
    }


    private com.example.model.domain.GeneralStats updateGeneralStats(com.example.model.domain.QuizResult quizResult, com.example.model.domain.GeneralStats generalStats) {
        updateGeneralStatsUseCase.execute(generalStats, quizResult.getScore(), (Timestamp) quizResult.getQuizTimeData().get("quizDate"));
        generalStats.calculateUserLevel(generalStats.getTotalScore());
        generalStats.incrementTotalScore(quizResult.getScore());
        generalStats.incrementNumberOfQuizzes();
        generalStats.updateLastQuizDate((Timestamp) quizResult.getQuizTimeData().get("quizDate"));
        Timestamp quizDate = getQuizDate(quizResult);
        // Calculate and update streaks
        int newCurrentStreak = generalStats.calculateCurrentStreak(quizDate);
        generalStats.setCurrentStreak(newCurrentStreak);
        generalStats.setLongestStreak(newCurrentStreak);
        return generalStats;
}
*/
}
