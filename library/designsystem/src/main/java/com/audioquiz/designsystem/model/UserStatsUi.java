package com.audioquiz.designsystem.model;


import java.util.Date;

public class UserStatsUi {
    private int id;
    private Date lastUpdated;
    private GeneralStatsUi generalStatsUi;
    private CategoryStatsUi categoryStatsUi;
    private FrequencyStatsUi frequencyStatsUi;
    private Last7DaysScoresUi last7DaysScoresUi;

    private UserStatsUi() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // GETTERS
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public GeneralStatsUi getGeneralStats() {
        return generalStatsUi;
    }
    public CategoryStatsUi getCategoryStats() {
        return categoryStatsUi;
    }
    public Last7DaysScoresUi getLast7DaysScores() {
        return last7DaysScoresUi;
    }
    public FrequencyStatsUi getFrequencyStats() {
        return frequencyStatsUi;
    }
    // SETTERS
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setGeneralStats(GeneralStatsUi generalStatsUi) {
        this.generalStatsUi = generalStatsUi;
    }
    public void setCategoryStats(CategoryStatsUi categoryStatsUi) {
        this.categoryStatsUi = categoryStatsUi;
    }
    public void setFrequencyStats(FrequencyStatsUi frequencyStatsUi) {
        this.frequencyStatsUi = frequencyStatsUi;
    }
    public void setLast7DaysScores(Last7DaysScoresUi last7DaysScoresUi) {
        this.last7DaysScoresUi = last7DaysScoresUi;
    }

}

