package com.audioquiz.data.local.entity.user_data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Map;

@Entity(tableName = "last_7_days_scores")
public class DailyScoresEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private Date lastUpdated;
    private Map<String, Integer> dailyScores;
    private int totalLast7Days;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getTotalLast7Days() {
        return totalLast7Days;
    }

    public void setTotalLast7Days(int totalLast7Days) {
        this.totalLast7Days = totalLast7Days;
    }

    public Map<String, Integer> getDailyScores() {
        return dailyScores;
    }

    public void setDailyScores(Map<String, Integer> dailyScores) {
        this.dailyScores = dailyScores;
    }

    @Ignore
    public DailyScoresEntity() {
    }

    public DailyScoresEntity(String userId, int totalLast7Days, Map<String, Integer> dailyScores) {
        this.userId = userId;
        this.totalLast7Days = totalLast7Days;
        this.dailyScores = dailyScores;
    }
}
