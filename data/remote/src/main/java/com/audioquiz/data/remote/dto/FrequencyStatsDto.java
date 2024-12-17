package com.audioquiz.data.remote.dto;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class FrequencyStatsDto {
    private static final String TAG = "com.example.model.FrequencyStatsDto";
    private Map<String, FrequencyStatsDataDto> pitchScoresMap;
    private Map<String, Integer> intervalScoresMap;

    private FrequencyStatsDto(Builder builder) {
        this.pitchScoresMap = builder.pitchScoresMap;
        this.intervalScoresMap = builder.intervalScoresMap;
    }

    // No-argument constructor
    private FrequencyStatsDto() {
        this.pitchScoresMap = new HashMap<>();
        this.intervalScoresMap = new HashMap<>();
    }

    // Factory method
    public static FrequencyStatsDto createDefault() {
        return new FrequencyStatsDto();
    }



    // GETTERS
    public Map<String, FrequencyStatsDataDto> getPitchScoresMap() {
        return pitchScoresMap;
    }

    public Map<String, Integer> getIntervalScoresMap() {
        return intervalScoresMap;
    }

    // SETTERS
    public void setPitchScoresMap(Map<String, FrequencyStatsDataDto> pitchScoresMap) {
        this.pitchScoresMap = pitchScoresMap;
    }

    public void setIntervalScoresMap(Map<String, Integer> intervalScoresMap) {
        this.intervalScoresMap = intervalScoresMap;
    }


    public static class Builder {
        private Map<String, FrequencyStatsDataDto> pitchScoresMap;
        private Map<String, Integer> intervalScoresMap;

        public Builder() {
            // No-argument constructor
        }

        public Builder setPitchScoresMap(Map<String, FrequencyStatsDataDto> pitchScoresMap) {
            this.pitchScoresMap = pitchScoresMap;
            return this;
        }

        public Builder setIntervalScoresMap(Map<String, Integer> intervalScoresMap) {
            this.intervalScoresMap = intervalScoresMap;
            return this;
        }

        public FrequencyStatsDto build() {
            return new FrequencyStatsDto(this);
        }
    }


    @Override
    @NonNull
    public String toString() {
        return "com.example.model.FrequencyStatsDto{" +
                "pitchScoresMap=" + pitchScoresMap +
                ", intervalScoresMap=" + intervalScoresMap +
                '}';
    }
}
