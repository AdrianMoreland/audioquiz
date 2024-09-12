package com.audioquiz.localdatasource.entity.user_data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "category_stats")
public class CategoryStatsEntity {
    @PrimaryKey(autoGenerate = true)
    private int categoryStatsId;
     private int categoryIndex;
    private Date lastUpdated;
    private String categoryName;
    private int currentChapter;
    private int numberOfQuizzes;
    private int totalQuestionsLearn;
    private int correctAnswersLearn;
    private int totalQuestionsCompetitive;
    private int correctAnswersCompetitive;
    private double totalTimeSpent;

    // Getters and setters
    public int getCategoryStatsId() {
        return categoryStatsId;
    }

    public void setCategoryStatsId(int categoryStatsId) {
        this.categoryStatsId = categoryStatsId;
    }

    public int getCategoryIndex() {
        return categoryIndex;
    }

    public Date getLastUpdated(){
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated){
        this.lastUpdated = lastUpdated;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCurrentChapter() {
        return currentChapter;
    }

    public void setCurrentChapter(int currentChapter) {
        this.currentChapter = currentChapter;
    }

    public int getNumberOfQuizzes() {
        return numberOfQuizzes;
    }

    public void setNumberOfQuizzes(int numberOfQuizzes) {
        this.numberOfQuizzes = numberOfQuizzes;
    }

    public int getTotalQuestionsLearn() {
        return totalQuestionsLearn;
    }

    public void setTotalQuestionsLearn(int totalQuestionsLearn) {
        this.totalQuestionsLearn = totalQuestionsLearn;
    }

    public int getCorrectAnswersLearn() {
        return correctAnswersLearn;
    }

    public void setCorrectAnswersLearn(int correctAnswersLearn) {
        this.correctAnswersLearn = correctAnswersLearn;
    }

    public int getTotalQuestionsCompetitive() {
        return totalQuestionsCompetitive;
    }

    public void setTotalQuestionsCompetitive(int totalQuestionsCompetitive) {
        this.totalQuestionsCompetitive = totalQuestionsCompetitive;
    }

    public int getCorrectAnswersCompetitive() {
        return correctAnswersCompetitive;
    }

    public void setCorrectAnswersCompetitive(int correctAnswersCompetitive) {
        this.correctAnswersCompetitive = correctAnswersCompetitive;
    }

    public double getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(double totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }


    // Usable public constructor
    public CategoryStatsEntity() {
        // Initialize fields here
    }
}

