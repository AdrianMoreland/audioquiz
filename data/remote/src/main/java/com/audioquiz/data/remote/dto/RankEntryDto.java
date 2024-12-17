package com.audioquiz.data.remote.dto;

public class RankEntryDto {
    private int id;
    private String userId;
    private String username;
    private String profileImage;
    private int totalScore;
    private double averageScore;
    private int weeklyScore;

    public RankEntryDto() {
    }

    public RankEntryDto(String userId, String username, String profileImage, int totalScore, double averageScore) {
        this.userId = userId;
        this.username = username;
        this.profileImage = profileImage;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }

    public RankEntryDto(String userId, String username, int totalScore, double averageScore) {
        this.userId = userId;
        this.username = username;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }

    public RankEntryDto(String userId, String username, int totalScore, int weeklyScore) {
        this.userId = userId;
        this.username = username;
        this.totalScore = totalScore;
        this.weeklyScore = weeklyScore;
    }

    // getters and setters
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

    public int getWeeklyScore() {
        return weeklyScore;
    }

    public void setWeeklyScore(int weeklyScore) {
        this.weeklyScore = weeklyScore;
    }
}
