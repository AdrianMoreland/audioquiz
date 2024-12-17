package com.audioquiz.designsystem.model;

public class CategoryStatsDataUi {
    private int id;
    private int categoryIndex;
    private String categoryName;
    private int currentChapter;
    private int numberOfQuizzes;
    private int totalQuestionsLearn;
    private int correctAnswersLearn;
    private int totalQuestionsCompetitive;
    private int correctAnswersCompetitive;
    private double totalTimeSpent;

    // No-argument constructor
    public CategoryStatsDataUi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //GETTERS
    public int getCategoryIndex() {
        return categoryIndex;
    }
    public String getCategoryName() {
        return categoryName;
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

}