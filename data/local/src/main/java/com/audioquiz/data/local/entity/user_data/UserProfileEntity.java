package com.audioquiz.data.local.entity.user_data;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Database DTO for a question
@Entity(tableName = "user_profile")
public class UserProfileEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userId;
    public String username;
    public String profileImage;
    public long dateCreated;

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
    public long getDateCreated() {
        return dateCreated;
    }
    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }
    // Usable public constructor
    public UserProfileEntity(String userId, String username, String profileImage, long dateCreated) {
        this.userId = userId;
        this.username = username;
        this.profileImage = profileImage;
        this.dateCreated = dateCreated;
    }

    // Default constructor
    @Ignore
    public UserProfileEntity() {
        // Default constructor
    }

}