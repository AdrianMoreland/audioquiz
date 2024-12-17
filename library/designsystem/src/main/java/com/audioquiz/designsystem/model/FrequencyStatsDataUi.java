package com.audioquiz.designsystem.model;


import java.util.Map;

public class FrequencyStatsDataUi {
    private int id;
    private int frequency;
    private int totalQuestions;
    private int correctAnswers;
    private Map<String, Integer> mistakes;

    public FrequencyStatsDataUi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // GETTERS
    public int getFrequency() {
        return frequency;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public  Map<String, Integer>  getMistakes() {
        return mistakes;
    }

    // SETTERS
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setMistakes( Map<String, Integer>  mistakes) {
        this.mistakes = mistakes;
    }

}
