package com.audioquiz.data.remote.dto;

import java.util.Date;

public class UserProfileDto {
        public Date lastUpdated;
        public String userId;
        public String username;
        public String profileImage;
        public Date dateCreated;

        public UserProfileDto() {
        }

        // getters and setters
        public Date getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
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

        public Date getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
        }

        public UserProfileDto(Date lastUpdated, String userId, String username, String profileImage, Date dateCreated) {
            this.lastUpdated = lastUpdated;
            this.userId = userId;
            this.username = username;
            this.profileImage = profileImage;
            this.dateCreated = dateCreated;
        }
}
