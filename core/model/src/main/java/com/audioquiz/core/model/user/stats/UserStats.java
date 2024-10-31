package com.audioquiz.core.model.user.stats;


import com.audioquiz.core.model.user.UserProfile;

import java.util.Date;


/**
 * Represents the statistics of a user.
 * [userId] The ID of the user.
 * [lastUpdated] The date when the statistics were last updated.
 * [generalStats] The general statistics of the user.
 * [categoryStats] The statistics of the user per category.
 * [frequencyStats] The statistics of the user per frequency.
 * [last7DaysScores] The scores of the user for the last 7 days.
 */
public class UserStats {
    private String userId;
    private Date lastUpdated;
    private UserProfile userProfile;
    private GeneralStats generalStats;
    private CategoryStats categoryStats;
    private FrequencyStats frequencyStats;
    private Last7DaysScores last7DaysScores;

    private UserStats() {
        this.lastUpdated = new Date();
        this.generalStats = GeneralStats.createDefault(userId);
        this.categoryStats = CategoryStats.createDefault(userId);
        this.frequencyStats = FrequencyStats.createDefault(userId);
        this.last7DaysScores = Last7DaysScores.createDefault(userId);
    }

    // Constructor with a Builder object
    private UserStats(Builder builder) {
        this.userId = builder.userId;
        this.lastUpdated = builder.lastUpdated;
        this.userProfile = builder.userProfile;
        this.generalStats = builder.generalStats;
        this.categoryStats = builder.categoryStats;
        this.frequencyStats = builder.frequencyStats;
    }

    // Factory method to create a default instance
    public static UserStats createDefault(String userId) {
        UserStats userStats = new UserStats();
        userStats.setUserId(userId);
        return userStats;
    }

    public UserStats createEmpty() {
        this.userId = "";
        this.lastUpdated = new Date();
        this.generalStats = null;
        this.categoryStats = null;
        this.frequencyStats = null;
        return null;
    }

    // GETTERS
    public String getUserId() {
        return userId;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public UserProfile getUserProfile() {
        return userProfile;
    }
    public GeneralStats getGeneralStats() {
        return generalStats;
    }
    public CategoryStats getCategoryStats() {
        return categoryStats;
    }
    public Last7DaysScores getLast7DaysScores() {
        return last7DaysScores;
    }
    public FrequencyStats getFrequencyStats() {
        return frequencyStats;
    }
    // SETTERS
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    public void setGeneralStats(GeneralStats generalStats) {
        this.generalStats = generalStats;
    }
    public void setCategoryStats(CategoryStats categoryStats) {
        this.categoryStats = categoryStats;
    }
    public void setFrequencyStats(FrequencyStats frequencyStats) {
        this.frequencyStats = frequencyStats;
    }
    public void setLast7DaysScores(Last7DaysScores last7DaysScores) {
        this.last7DaysScores = last7DaysScores;
    }

    // BUILDER
    public static class Builder {
        private String userId;
        private Date lastUpdated;
        private UserProfile userProfile;
        private GeneralStats generalStats;
        private CategoryStats categoryStats;
        private Last7DaysScores last7DaysScores;
        private FrequencyStats frequencyStats;

        public Builder() {
            // No-argument constructor
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }
        public Builder setUserProfile(UserProfile userProfile) {
            this.userProfile = userProfile;
            return this;
        }
        public Builder setGeneralStats(GeneralStats generalStats) {
            this.generalStats = generalStats;
            return this;
        }
        public Builder setCategoryStats(CategoryStats categoryStats) {
            this.categoryStats = categoryStats;
            return this;
        }
        public Builder setFrequencyStats(FrequencyStats frequencyStats) {
            this.frequencyStats = frequencyStats;
            return this;
        }



        public UserStats build() {
            return new UserStats(this);
        }
    }



    @Override
    public String toString() {
        return "com.example.model.domain.UserStats{" +
                "userId='" + userId + '\'' +
                "lastUpdated=" + lastUpdated +
                ", generalStats=" + generalStats.toString() +
                ", categoryStats=" + categoryStats.toString() +
                ", frequencyStats=" + frequencyStats +
                ", last7DaysScores=" + last7DaysScores +
                '}';
    }


}

