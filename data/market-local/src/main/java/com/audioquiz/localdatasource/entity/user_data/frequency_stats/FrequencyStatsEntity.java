package com.audioquiz.localdatasource.entity.user_data.frequency_stats;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.adrian.database.converter.FrequencyStatsConverter;

import java.util.Map;

@Entity(tableName = "frequency_stats")
@TypeConverters({FrequencyStatsConverter.class})
public class FrequencyStatsEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private Map<String, PitchStatsEntity> frequencyStatsMap;
    private Map<String, PitchStatsEntity> intervalScoresMap;

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
    public Map<String, PitchStatsEntity> getFrequencyStatsMap() {
        return frequencyStatsMap;
    }
    public void setFrequencyStatsMap(Map<String, PitchStatsEntity> frequencyStatsMap) {
        this.frequencyStatsMap = frequencyStatsMap;
    }
    public Map<String, PitchStatsEntity> getIntervalScoresMap() {
        return intervalScoresMap;
    }
    public void setIntervalScoresMap(Map<String, PitchStatsEntity> intervalScoresMap) {
        this.intervalScoresMap = intervalScoresMap;
    }
}