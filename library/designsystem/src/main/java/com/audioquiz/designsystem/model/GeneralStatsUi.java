package com.audioquiz.designsystem.model;

import java.util.Date;

public class GeneralStatsUi {
    private int id;
    private int userLevel;
    private int numberOfLives;
    private int numberOfQuizzes;
    private int numberOfQuestions;
    private int totalScore;
    private double averageScore;
    private int currentStreak;
    private int longestStreak;
    private Date lastQuizDate;

    public GeneralStatsUi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //GETTERS
    public int getUserLevel() {
        return userLevel;
    }
    public int getTotalScore() {
        return totalScore;
    }
    public double getAverageScore() {
        return averageScore;
    }
    public int getNumberOfQuizzes() {
        return numberOfQuizzes;
    }
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
    public int getNumberOfLives() {
        return numberOfLives;
    }
    public int getLongestStreak() {
        return longestStreak;
    }
    public int getCurrentStreak() {
        return currentStreak;
    }
    public Date getLastQuizDate() {
        return lastQuizDate;
    }
    //SETTERS
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }
    public void setNumberOfQuizzes(int numberOfQuizzes) {
        this.numberOfQuizzes = numberOfQuizzes;
    }
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }
    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }
    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }
    public void setLastQuizDate(Date lastQuizDate) {
        this.lastQuizDate = lastQuizDate;
    }

    }
