package com.audioquiz.designsystem.model;

import java.util.Map;

public class Last7DaysScoresUi {
    private int id;
    private Map<String, Integer> dailyScores;
    private int totalLast7Days;

    private Last7DaysScoresUi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // GETTERS
    public Map<String, Integer> getDailyScores() {
        return dailyScores;
    }
    public int getTotalLast7Days() {
        return totalLast7Days;
    }
    // SETTERS
    public void setDailyScores(Map<String, Integer> dailyScores) {
        this.dailyScores = dailyScores;
    }
    public void setTotalLast7Days(int totalLast7Days) {
        this.totalLast7Days = totalLast7Days;
    }
}
