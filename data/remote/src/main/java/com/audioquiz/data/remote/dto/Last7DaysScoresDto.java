package com.audioquiz.data.remote.dto;

import androidx.annotation.NonNull;

import java.util.Map;

public class Last7DaysScoresDto {
    public Map<String, Integer> dailyScores;
    public int totalLast7Days;

    // No-argument constructor required for Firestore's automatic data mapping
    public Last7DaysScoresDto() {}


    @NonNull
    @Override
    public String toString() {
        return "com.example.model.Last7DaysScoresDto{" +
                "dailyScores=" + dailyScores +
                ", totalScoreLast7Days=" + totalLast7Days +
                '}';
    }

}
