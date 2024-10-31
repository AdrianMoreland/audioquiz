package com.audioquiz.data.local.entity.rank_stats;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


// Database DTO for a question
@Entity(tableName = "rankAllTime")
public class RankAllTimeEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userId;
    public String username;
    public String profileImage;
    public int totalScore;
    public double averageScore;

    public RankAllTimeEntity() {

    }

    @Ignore
    public RankAllTimeEntity(String userId, String username, String profileImage, int totalScore, double averageScore) {
        this.userId = userId;
        this.username = username;
        this.profileImage = profileImage;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }

    @Ignore
    public RankAllTimeEntity(String userId, String username, int totalScore, double averageScore) {
        this.userId = userId;
        this.username = username;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }
    @Ignore
    public RankAllTimeEntity(String userId, String username, int totalScore, int weeklyScore) {
        this.userId = userId;
        this.username = username;
        this.totalScore = totalScore;
    }

    //Getters and Setters
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
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



}
