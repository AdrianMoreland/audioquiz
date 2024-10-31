package com.audioquiz.core.model.user.stats;


import java.util.Date;

/**
 * Represents the statistics of a user per category.
 * [categoryIndex] The index of the category.
 * [categoryName] The name of the category.
 * [currentChapter] The current chapter of the category.
 * [numberOfQuizzes] The number of quizzes taken.
 * [totalQuestionsLearn] The total number of questions answered in learn mode.
 * [correctAnswersLearn] The total number of correct answers in learn mode.
 * [totalQuestionsCompetitive] The total number of questions answered in competitive mode.
 * [correctAnswersCompetitive] The total number of correct answers in competitive mode.
 * [totalTimeSpent] The total time spent in the category.
 */
public class CategoryStatsData  {
    private int categoryIndex;
    private String categoryName;
    private Date lastUpdated;
    private int currentChapter;
    private int numberOfQuizzes;
    private int totalQuestionsLearn;
    private int correctAnswersLearn;
    private int totalQuestionsCompetitive;
    private int correctAnswersCompetitive;
    private double totalTimeSpent;

    // No-argument constructor
    public CategoryStatsData() {
        this.categoryIndex = 0;
        this.categoryName = "categoryName";
        this.lastUpdated = new Date();
        this.currentChapter = 1;
        this.numberOfQuizzes = 0;
        this.totalQuestionsLearn = 0;
        this.correctAnswersLearn = 0;
        this.totalQuestionsCompetitive = 0;
        this.correctAnswersCompetitive = 0;
        this.totalTimeSpent = 0.0;
    }

    public static CategoryStatsData createDefault(String categoryName) {
       CategoryStatsData categoryStatsData = new CategoryStatsData();
        categoryStatsData = categoryStatsData.updateInitialCategoryStatsData(categoryName);
        return categoryStatsData;
    }

    // Helper methods to get and set com.example.model.domain.CategoryStatsData
    public CategoryStatsData updateInitialCategoryStatsData(String categoryName) {
        switch (categoryName) {
            case "SoundWaves":
                this.categoryIndex = 0;
                this.categoryName = "SoundWaves";
                break;
            case "Synthesis":
                this.categoryIndex = 1;
                this.categoryName = "Synthesis";
                break;
            case "Production":
                this.categoryIndex = 2;
                this.categoryName = "Production";
                break;
            case "Mixing":
                this.categoryIndex = 3;
                this.categoryName = "Mixing";
                break;
            case "Processing":
                this.categoryIndex = 4;
                this.categoryName = "Processing";
                break;
            case "MusicTheory":
                this.categoryIndex = 5;
                this.categoryName = "MusicTheory";
                break;
            case "Pitch":
                this.categoryIndex = 6;
                this.categoryName = "Pitch";
                break;
            case "Interval":
                this.categoryIndex = 7;
                this.categoryName = "Interval";
                break;
            default:
                this.categoryIndex = 0;
                this.categoryName = "N/A";
        }
        return this;
    }
    
    private CategoryStatsData(Builder builder) {
        this.categoryIndex = builder.categoryIndex;
        this.categoryName  = builder.categoryName;
        this.lastUpdated = builder.lastUpdated;
        this.currentChapter = builder.currentChapter;
        this.numberOfQuizzes = builder.numberOfQuizzes;
        this.totalQuestionsLearn = builder.totalQuestionsLearn;
        this.correctAnswersLearn = builder.correctAnswersLearn;
        this.totalQuestionsCompetitive = builder.totalQuestionsCompetitive;
        this.correctAnswersCompetitive = builder.correctAnswersCompetitive;
        this.totalTimeSpent = builder.totalTimeSpent;
    }

    //GETTERS
    public int getCategoryIndex() {
        return categoryIndex;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public int getTotalQuestionsLearn() {
        return totalQuestionsLearn;
    }
    public int getCorrectAnswersLearn() {
        return correctAnswersLearn;
    }
    public int getNumberOfQuizzes() {
        return numberOfQuizzes;
    }
    public int getCurrentChapter() {
        return currentChapter;
    }
    public int getTotalQuestionsCompetitive() {
        return totalQuestionsCompetitive;
    }
    public int getCorrectAnswersCompetitive() {
        return correctAnswersCompetitive;
    }
    public double getTotalTimeSpent() {
        return totalTimeSpent;
    }

    //SETTERS
    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setTotalQuestionsLearn(int totalQuestionsLearn) {
        this.totalQuestionsLearn = totalQuestionsLearn;
    }
    public void setCorrectAnswersLearn(int correctAnswersLearn) {
        this.correctAnswersLearn = correctAnswersLearn;
    }
    public void setNumberOfQuizzes(int numberOfQuizzes) {
        this.numberOfQuizzes = numberOfQuizzes;
    }
    public void setCurrentChapter(int currentChapter) {
        this.currentChapter = currentChapter;
    }
    public void setTotalQuestionsCompetitive(int totalQuestionsCompetitive) {
        this.totalQuestionsCompetitive = totalQuestionsCompetitive;
    }
    public void setCorrectAnswersCompetitive(int correctAnswersCompetitive) {
        this.correctAnswersCompetitive = correctAnswersCompetitive;
    }
    public void setTotalTimeSpent(double totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    //BUILDER
    public static class Builder {
        private int categoryIndex;
        private String categoryName;
        private Date lastUpdated;
        private int totalQuestionsLearn;
        private int correctAnswersLearn;
        private int numberOfQuizzes;
        private int currentChapter;
        private int totalQuestionsCompetitive;
        private int correctAnswersCompetitive;
        private double totalTimeSpent;


        public Builder() {
            // No-argument constructor
            // This is needed for Firebase's automatic data mapping.
            // Firebase uses this no-argument constructor to instantiate the object,
            // and then uses the setter methods to set the fields.
        }
        public Builder setCategoryIndex (int categoryIndex) {
            this.categoryIndex = categoryIndex;
            return this;
        }
        public Builder setLastUpdated (Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }
        public Builder setCategoryName (String categoryName) {
            this.categoryName = categoryName;
            return this;
        }
        public Builder setCorrectAnswersLearn(int correctAnswersLearn) {
            this.correctAnswersLearn = correctAnswersLearn;
            return this;
        }
        public Builder setTotalQuestionsLearn(int totalQuestionsLearn) {
            this.totalQuestionsLearn = totalQuestionsLearn;
            return this;
        }
        public Builder setNumberOfQuizzes(int numberOfQuizzes) {
            this.numberOfQuizzes = numberOfQuizzes;
            return this;
        }
        public Builder setCurrentChapter(int currentChapter) {
            this.currentChapter = currentChapter;
            return this;
        }
        public Builder setTotalQuestionsCompetitive(int totalQuestionsCompetitive) {
            this.totalQuestionsCompetitive = totalQuestionsCompetitive;
            return this;
        }
        public Builder setCorrectAnswersCompetitive(int correctAnswersCompetitive) {
            this.correctAnswersCompetitive = correctAnswersCompetitive;
            return this;
        }
        public Builder setTotalTimeSpent(double totalTimeSpent) {
            this.totalTimeSpent = totalTimeSpent;
            return this;
        }

        public CategoryStatsData build() {
            return new CategoryStatsData(this);
        }
    }

    @Override
    public String toString() {
        return "com.example.model.domain.CategoryStatsData{" +
                "categoryIndex=" + categoryIndex +
                "categoryName=" + categoryName +
                ", lastUpdated=" + lastUpdated +
                "currentChapter=" + currentChapter +
                ", numberOfQuizzes=" + numberOfQuizzes +
                ", totalQuestionsLearn=" + totalQuestionsLearn +
                ", correctAnswersLearn=" + correctAnswersLearn +
                ", totalQuestionsCompetitive=" + totalQuestionsCompetitive +
                ", correctAnswersCompetitive=" + correctAnswersCompetitive +
                ", totalTimeSpent=" + totalTimeSpent +
                '}';
    }
}