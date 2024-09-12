package com.audioquiz.localdatasource.entity.user_data.frequency_stats;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Map;

@Entity(tableName = "frequency_stats_data")
public class PitchStatsEntity {
    @PrimaryKey()
    @NonNull
    private String id;
    private int frequency;
    private int totalQuestions;
    private int correctAnswers;
    private Map<String, Integer> mistakes;

    public PitchStatsEntity() {
    }

    // Getters and setters
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Map<String, Integer> getMistakes() {
        return mistakes;
    }

    public void setMistakes(Map<String, Integer> mistakes) {
        this.mistakes = mistakes;
    }
}

