package com.audioquiz.data.local.entity.user_data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.audioquiz.data.local.converter.CategoryStatsTypeConverter;
import com.audioquiz.data.local.converter.FrequencyStatsTypeConverter;
import com.audioquiz.data.local.converter.GeneralStatsTypeConverter;
import com.audioquiz.data.local.converter.UserProfileTypeConverter;
import com.audioquiz.data.local.entity.user_data.frequency_stats.FrequencyStatsEntity;

import java.util.Date;

@Entity(tableName = "user_stats")
@TypeConverters({UserProfileTypeConverter.class, GeneralStatsTypeConverter.class, CategoryStatsTypeConverter.class, FrequencyStatsTypeConverter.class})
public class UserStatsEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private Date lastUpdated;
    private UserProfileEntity userProfile;
    private GeneralStatsEntity generalStats;
    private CategoryStatsEntity categoryStats;
    private FrequencyStatsEntity frequencyStats;
    private DailyScoresEntity dailyScores;

    // Usable public constructor
    public UserStatsEntity(String userId,
                           UserProfileEntity userProfile,
                           GeneralStatsEntity generalStats,
                           CategoryStatsEntity categoryStats,
                           FrequencyStatsEntity frequencyStats,
                           DailyScoresEntity dailyScores) {
        this.userId = userId;
        this.lastUpdated = new Date();
        this.userProfile = userProfile;
        this.generalStats = generalStats;
        this.categoryStats = categoryStats;
        this.frequencyStats = frequencyStats;
        this.dailyScores = dailyScores;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public UserProfileEntity getUserProfile() {
        return userProfile;
    }

    public GeneralStatsEntity getGeneralStats() {
        return generalStats;
    }

    public FrequencyStatsEntity getFrequencyStats() {
        return frequencyStats;
    }

    public CategoryStatsEntity getCategoryStats() {
        return categoryStats;
    }

    public DailyScoresEntity getDailyScores() {
        return dailyScores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }

    public void setGeneralStats(GeneralStatsEntity generalStats) {
        this.generalStats = generalStats;
    }

    public void setFrequencyStats(FrequencyStatsEntity frequencyStats) {
        this.frequencyStats = frequencyStats;
    }

    public void setCategoryStats(CategoryStatsEntity categoryStats) {
        this.categoryStats = categoryStats;
    }

    public void setDailyScores(DailyScoresEntity dailyScores) {
        this.dailyScores = dailyScores;
    }


/*    @Embedded
    private GeneralStatsEntity generalStats;

    @Relation(parentColumn = "id", entityColumn = "userId")
    private FrequencyStatsEntity frequencyStats;

    @Relation(parentColumn = "id", entityColumn = "userId")
    private CategoryStatsEntity categoryStats;

    @Embedded
    private DailyScoresEntity last7DaysScores;*/

}

