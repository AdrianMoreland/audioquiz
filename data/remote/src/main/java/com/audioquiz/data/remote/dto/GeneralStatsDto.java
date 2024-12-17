package com.audioquiz.data.remote.dto;

import androidx.annotation.NonNull;

import java.util.Date;

public class GeneralStatsDto {
        public int userLevel;
        public int numberOfLives;
        public int numberOfQuizzes;
        public int numberOfQuestions;
        public int totalScore;
        public double averageScore;
        public int currentStreak;
        public int longestStreak;
        public Date lastQuizDate;

        // No-argument constructor required for Firestore's automatic data mapping
        public GeneralStatsDto() {}

    // getters and setters
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

    @NonNull
    @Override
    public String toString() {
        return "GeneralStatsDto{" +
                "totalScore=" + totalScore +
                ", userLevel=" + userLevel +
                ", averageScore=" + averageScore +
                ", numberOfQuizzes=" + numberOfQuizzes +
                ", numberOfQuestions=" + numberOfQuestions +
                ", numberOfLives=" + numberOfLives +
                ", longestStreak=" + longestStreak +
                ", currentStreak=" + currentStreak +
                ", lastQuizDate=" + lastQuizDate +
                '}';
    }

}
