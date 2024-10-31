package com.audioquiz.data.local.entity.user_data;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "general_stats")
public class GeneralStatsEntity {
    @PrimaryKey()
    @NonNull
    private String userId;
    private Date lastUpdated;
    private int userLevel;
    private int numberOfLives;
    private int numberOfQuizzes;
    private int numberOfQuestions;
    private int totalScore;
    private double averageScore;
    private int currentStreak;
    private int longestStreak;
    private Date lastQuizDate;

    public GeneralStatsEntity() {

    }

    @Ignore
    public GeneralStatsEntity(@NonNull String userId, Date lastUpdated, int userLevel, int numberOfLives, int numberOfQuizzes, int numberOfQuestions, int totalScore, double averageScore, int currentStreak, int longestStreak, Date lastQuizDate) {
        this.userId = userId;
        this.lastUpdated = new Date();
        this.userLevel = userLevel;
        this.numberOfLives = numberOfLives;
        this.numberOfQuizzes = numberOfQuizzes;
        this.numberOfQuestions = numberOfQuestions;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.lastQuizDate = lastQuizDate;

    }

    // Getters and setters
    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    public int getNumberOfQuizzes() {
        return numberOfQuizzes;
    }

    public void setNumberOfQuizzes(int numberOfQuizzes) {
        this.numberOfQuizzes = numberOfQuizzes;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public Date getLastQuizDate() {
        return lastQuizDate;
    }

    public void setLastQuizDate(Date lastQuizDate) {
        this.lastQuizDate = lastQuizDate;
    }
}