package com.audioquiz.core.domain.usecase.user.stats.stats;


import com.audioquiz.core.model.quiz.QuizResult;
import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.core.model.user.stats.CategoryStatsData;

import java.util.Map;

import javax.inject.Inject;


public class UpdateCategoryStatsUseCaseImpl {
    private static final String TAG = "UpdateCategoryStatsUseCaseImpl";

    @Inject
    public UpdateCategoryStatsUseCaseImpl() {
    }

    public CategoryStats execute (QuizResult quizResult, CategoryStats categoryStats) {
        String quizType = quizResult.getQuizType();
        String category = quizResult.getCategory();

        if (quizType.equalsIgnoreCase("LESSON")) {
            updateLessonProgression(categoryStats, quizResult);
        } else if (quizType.equalsIgnoreCase("COMPETITIVE")) {
            updateCompetitiveProgression(categoryStats, quizResult);
        } else if (category.equalsIgnoreCase("pitch")) {
            updateCompetitiveProgression(categoryStats, quizResult);
        } else {
            System.out.println("UpdateCategoryStatsUsecase" + "An error has occurred: " + quizResult.getCategory() + quizResult.getChapter());
        }
        return categoryStats;
    }

    private void updateLessonProgression(CategoryStats categoryStats, QuizResult quizResult) {
        int score = quizResult.getScore();
        String category = quizResult.getCategory();
        int chapter = quizResult.getChapter();
        CategoryStatsData categoryStatsData = categoryStats.getCategoryStatsData(category);

        int currentChapter = categoryStatsData != null ? categoryStatsData.getCurrentChapter() : 1;
        int numberOfQuizzes = categoryStatsData != null ? categoryStatsData.getNumberOfQuizzes() : 1;
        int correctAnswersLearn = categoryStatsData != null ? categoryStatsData.getCorrectAnswersLearn() : 1;
        int totalQuestionsLearn = categoryStatsData != null ? categoryStatsData.getTotalQuestionsLearn() : 1;

        if (chapter < 4) {
            totalQuestionsLearn += 7;
            if (quizResult.getScore() > 2) {
                currentChapter += 1;
            } else {
                System.out.println("UpdateCategoryStatsUsecase" + "Score is less than passing score");
            }
        } else if (chapter == 4) {
            totalQuestionsLearn += 15;
            if (score > 11) {
                currentChapter += 1;
            } else {
                System.out.println("UpdateCategoryStatsUsecase" + "Score is less than passing score");
            }
        } else {
            System.out.println("UpdateCategoryStatsUsecase" + "Chapter is greater than 4");
        }

        CategoryStatsData updatedProgressionData = new CategoryStatsData.Builder()
                .setCurrentChapter(currentChapter)
                .setNumberOfQuizzes(numberOfQuizzes)
                .setTotalQuestionsLearn(totalQuestionsLearn)
                .setCorrectAnswersLearn(correctAnswersLearn)
                .build();

        categoryStats.setCategoryStatsData(category, updatedProgressionData);
    }

    private void updateCompetitiveProgression(CategoryStats categoryStats, QuizResult quizResult) {
        Map<String, Integer> scorePerCategory = quizResult.getScorePerCategory();
        System.out.println("UpdateCategoryStatsUsecase" + "updateCompetitiveProgression: " + scorePerCategory);
        for (Map.Entry<String, CategoryStatsData> entry : categoryStats.getAllCategoryStatsData().entrySet()) {
            String category = entry.getKey();
            CategoryStatsData statsData = entry.getValue();

            if (scorePerCategory.containsKey(category)) {
                Integer scoreInteger = scorePerCategory.get(category);
                int score = scoreInteger != null ? scoreInteger : 0;
                statsData.setCorrectAnswersCompetitive(statsData.getCorrectAnswersCompetitive() + score);
                statsData.setTotalQuestionsCompetitive(statsData.getTotalQuestionsCompetitive() + 10);
            }
        }
    }
}
