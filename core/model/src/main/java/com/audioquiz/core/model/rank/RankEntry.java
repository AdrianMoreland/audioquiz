package com.audioquiz.core.model.rank;


/**
 * Represents a rank entry in the leaderboard.
 * [id] represents the identifier of the rank entry.
 * [userId] represents the identifier of the user.
 * [username] represents the username of the user.
 * [profileImage] represents the profile image of the user.
 * [totalScore] represents the total score of the user.
 * [averageScore] represents the average score of the user.
 * [weeklyScore] represents the weekly score of the user.
 */
public class RankEntry {
    private int id;
    private String userId;
    private String username;
    private String profileImage;
    private int totalScore;
    private double averageScore;
    private int weeklyScore;

public RankEntry() {

    }

    public RankEntry(String userId, String username, String profileImage, int totalScore, double averageScore) {
        this.userId = userId;
        this.username = username;
        this.profileImage = profileImage;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }

    public RankEntry(String userId, String username, int totalScore, double averageScore) {
        this.userId = userId;
        this.username = username;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
    }

    public RankEntry(String userId, String username, int totalScore, int weeklyScore) {
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
