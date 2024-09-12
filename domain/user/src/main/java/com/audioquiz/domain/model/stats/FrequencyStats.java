package com.adrian.model.stats;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the statistics of a user per frequency.
 * [pitchScoresMap] The scores of the user per pitch.
 * [intervalScoresMap] The scores of the user per interval.
 */
public class FrequencyStats {
    private String id;
    private Date lastUpdated;
    private Map<String, PitchStats> pitchScoresMap;
    private Map<String, IntervalStats> intervalScoresMap;

    private FrequencyStats(Builder builder) {
        this.id = builder.id;
        this.lastUpdated = builder.lastUpdated;
        this.pitchScoresMap = builder.pitchScoresMap;
        this.intervalScoresMap = builder.intervalScoresMap;
    }

    // No-argument constructor
    private FrequencyStats() {
        this.lastUpdated = new Date();
        this.pitchScoresMap = new HashMap<>();
        this.intervalScoresMap = new HashMap<>();
    }

    // Factory method
    public static FrequencyStats createDefault(String id) {
        FrequencyStats frequencyStats = new FrequencyStats();
        frequencyStats.setId(id);
        return new FrequencyStats();
    }

    // GETTERS
    public String getId() {
        return id;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public Map<String, PitchStats> getPitchScoresMap() {
        return pitchScoresMap;
    }
    public Map<String, IntervalStats> getIntervalScoresMap() {
        return intervalScoresMap;
    }

    // SETTERS
    public void setId(String id) {
        this.id = id;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setPitchScoresMap(Map<String, PitchStats> pitchScoresMap) {
        this.pitchScoresMap = pitchScoresMap;
    }
    public void setIntervalScoresMap(Map<String, IntervalStats> intervalScoresMap) {
        this.intervalScoresMap = intervalScoresMap;
    }


    public static class Builder {
        private String id;
        private Date lastUpdated;
        private Map<String, PitchStats> pitchScoresMap;
        private Map<String, IntervalStats> intervalScoresMap;

        public Builder() {
            // No-argument constructor
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder setPitchScoresMap(Map<String, PitchStats> pitchScoresMap) {
            this.pitchScoresMap = pitchScoresMap;
            return this;
        }

        public Builder setIntervalScoresMap(Map<String, IntervalStats> intervalScoresMap) {
            this.intervalScoresMap = intervalScoresMap;
            return this;
        }

        public FrequencyStats build() {
            return new FrequencyStats(this);
        }
    }


    @Override
    public String toString() {
        return "com.example.model.domain.FrequencyStats{" +
                "pitchScoresMap=" + pitchScoresMap +
                ", intervalScoresMap=" + intervalScoresMap +
                '}';
    }
}
