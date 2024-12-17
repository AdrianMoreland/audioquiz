package com.audioquiz.core.model.auth;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {
    private final long lastUpdated;
    private final String userId;
    private final String displayName;
    private final String email;


    public LoggedInUser(long lastUpdated, String userId, String displayName, String email) {
        this.lastUpdated = lastUpdated;
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

}