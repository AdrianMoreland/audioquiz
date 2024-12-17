package com.audioquiz.feature.login.domain;



/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private long lastUpdated;
    private String userId;
    private final String username;
    private String email;

    public LoggedInUserView(long lastUpdated, String userId, String username, String email) {
        this.lastUpdated = lastUpdated;
        this.userId = userId;
        this.username = username;
        this.email = email;
     }

    public LoggedInUserView(String username) {
        this.username = username;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}