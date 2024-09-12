package com.audioquiz.localdatasource.entity.user_data;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database DTO for a question
@Entity(tableName = "user_profile")
public class UserProfileEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userId;
    public int username;
    public int profileImage;
    public String dateCreated;

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
    public int getUsername() {
        return username;
    }
    public void setUsername(int username) {
        this.username = username;
    }
    public int getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }
    public String getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
    // Usable public constructor
    public UserProfileEntity(String userId, int username, int profileImage, String dateCreated) {
        this.userId = userId;
        this.username = username;
        this.profileImage = profileImage;
        this.dateCreated = dateCreated;
    }
}