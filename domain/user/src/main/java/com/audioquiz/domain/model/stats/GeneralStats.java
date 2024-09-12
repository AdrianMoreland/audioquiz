package com.adrian.model.stats;

import java.util.Date;


/**
 * Represents the general statistics of a user.
 * [userLevel] The level of the user.
 * [numberOfLives] The number of lives the user has.
 * [totalScore] The total score of the user.
 * [averageScore] The average score of the user.
 * [numberOfQuizzes] The number of quizzes the user has taken.
 * [numberOfQuestions] The number of questions the user has answered.
 * [currentStreak] The current streak of the user.
 * [longestStreak] The longest streak of the user.
 * [lastQuizDate] The date when the user last took a quiz.
 */
public class GeneralStats {
    private String id;
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

    public GeneralStats() {
        this.lastUpdated = new Date();
        this.userLevel = 1;
        this.numberOfLives = 3;
        this.totalScore = 0;
        this.averageScore = 0.0;
        this.numberOfQuizzes = 0;
        this.numberOfQuestions = 0;
        this.longestStreak = 0;
        this.currentStreak = 0;
        this.lastQuizDate = new Date();
    }

    // Factory method to create a default instance
    public static GeneralStats createDefault(String id) {
        GeneralStats generalStats = new GeneralStats();
        generalStats.setId(id);
        return generalStats;
    }

    private GeneralStats(Builder builder) {
        this.id = builder.id;
        this.lastUpdated = builder.lastUpdated;
        this.userLevel = builder.userLevel;
        this.numberOfLives = builder.numberOfLives;
        this.totalScore = builder.totalScore;
        this.averageScore = builder.averageScore;
        this.numberOfQuizzes = builder.numberOfQuizzes;
        this.numberOfQuestions = builder.numberOfQuestions;
        this.longestStreak = builder.longestStreak;
        this.currentStreak = builder.currentStreak;
        this.lastQuizDate = builder.lastQuizDate;
    }

    //GETTERS
    public String getId() {
        return id;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
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
    public void setId(String id) {
        this.id = id;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
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

    public static class Builder {
        private String id;
        private Date lastUpdated;
        private int userLevel;
        private int totalScore;
        private double averageScore;
        private int numberOfQuizzes;
        private int numberOfQuestions;
        private int numberOfLives;
        private int longestStreak;
        private int currentStreak;
        private Date lastQuizDate;

        public Builder() {
            // No-argument constructor
            // This is needed for Firebase's automatic data mapping.
            // Firebase uses this no-argument constructor to instantiate the object,
            // and then uses the setter methods to set the fields.
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }
        public Builder setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }
        public Builder setUserLevel(int userLevel) {
            this.userLevel = userLevel;
            return this;
        }
        public Builder setTotalScore(int totalScore) {
            this.totalScore = totalScore;
            return this;
        }
        public Builder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }
        public Builder setNumberOfQuizzes(int numberOfQuizzes) {
            this.numberOfQuizzes = numberOfQuizzes;
            return this;
        }
        public Builder setNumberOfQuestions(int numberOfQuestions) {
            this.numberOfQuestions = numberOfQuestions;
            return this;
        }
        public Builder setNumberOfLives(int numberOfLives) {
            this.numberOfLives = numberOfLives;
            return this;
        }
        public Builder setLongestStreak(int longestStreak) {
            this.longestStreak = longestStreak;
            return this;
        }
        public Builder setCurrentStreak(int currentStreak) {
            this.currentStreak = currentStreak;
            return this;
        }
        public Builder setLastQuizDate(Date lastQuizDate) {
            this.lastQuizDate = lastQuizDate;
            return this;
        }
        public GeneralStats build() {
            return new GeneralStats(this);
        }
    }


    @Override
    public String toString() {
        return "com.example.model.domain.GeneralStats{" +
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
