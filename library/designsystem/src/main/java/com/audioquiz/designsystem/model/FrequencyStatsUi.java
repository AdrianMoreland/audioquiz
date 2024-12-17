package com.audioquiz.designsystem.model;

import java.util.Map;

public class FrequencyStatsUi {
    private int id;
    private Map<String, FrequencyStatsDataUi> pitchScoresMap;
    private Map<String, Integer> intervalScoresMap;

    private FrequencyStatsUi() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // GETTERS
    public Map<String, FrequencyStatsDataUi> getPitchScoresMap() {
        return pitchScoresMap;
    }

    public Map<String, Integer> getIntervalScoresMap() {
        return intervalScoresMap;
    }

    // SETTERS
    public void setPitchScoresMap(Map<String, FrequencyStatsDataUi> pitchScoresMap) {
        this.pitchScoresMap = pitchScoresMap;
    }

    public void setIntervalScoresMap(Map<String, Integer> intervalScoresMap) {
        this.intervalScoresMap = intervalScoresMap;
    }

}
