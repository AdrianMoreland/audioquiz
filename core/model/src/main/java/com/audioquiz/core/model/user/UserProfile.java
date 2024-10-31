package com.audioquiz.core.model.user;

/**
 * Represents a user profile.
 * [lastUpdated] The date the profile was last updated.
 * [userId] The identifier of the user.
 * [username] The name of the user.
 * [profileImage] The image of the user.
 * [dateCreated] The date the profile was created.
 */
public class UserProfile {
    private long lastUpdated;
    private String userId;
    private String username;
    private String profileImage;
    private long dateCreated;


    public UserProfile() {
    // Default constructor required for calls to DataSnapshot.getValue(com.example.model.domain.UserProfile.class)
    }

    private UserProfile(String userId, String username) {
        this.lastUpdated = System.currentTimeMillis();
        this.userId = userId;
        this.username = username;
        this.profileImage = "NoProfileImage";
        this.dateCreated = System.currentTimeMillis();
    }

    public static UserProfile createDefault(String userId, String username) {
        return new UserProfile(userId, username);
    }

    private UserProfile(Builder builder) {
        this.lastUpdated = builder.lastUpdated;
        this.userId = builder.userId;
        this.username = builder.username;
        this.profileImage = builder.profileImage;
        this.dateCreated = builder.dateCreated;}

    public long getLastUpdated() {
        return lastUpdated;
    }
    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public long getDateCreated() {
        return dateCreated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }



    public static class Builder {
        private long lastUpdated;
        private String userId;
        private String username;
        private String profileImage;
        private long dateCreated;

        public Builder() {
        }

        public Builder lastUpdated(long lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }


        public Builder profileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public Builder dateCreated(long dateCreated) {
            this.dateCreated = dateCreated;
            return this;
        }

        public UserProfile build() {
            return new UserProfile(this);
        }
    }


    @Override
    public String toString() {
        return "com.example.model.domain.UserProfile{" +
                "lastUpdated= " + lastUpdated + '\'' +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", dateCreated=" + dateCreated +
                '}';
    }



}
