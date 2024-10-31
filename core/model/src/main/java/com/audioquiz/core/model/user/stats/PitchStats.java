package com.audioquiz.core.model.user.stats;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the statistics of a user per frequency.
 * [frequency] The frequency of the questions.
 * [totalQuestions] The total number of questions answered by the user.
 * [correctAnswers] The total number of correct answers given by the user.
 * [mistakes] The mistakes made by the user with
 * String = frequency Id of the correct option
 * and
 * Integer = number of mistakes with that frequency.
 */
public class PitchStats {
    private String id;
    private Date lastUpdated;
    private String frequency;
    private int totalQuestions;
    private int correctAnswers;
    private Map<String, Integer> mistakes;

    public PitchStats() {
        this.frequency = "";
        this.totalQuestions = 0;
        this.correctAnswers = 0;
        this.mistakes = new HashMap<>();
    }

    public static PitchStats createDefault(String frequency) {
        PitchStats pitchStats = new PitchStats();
        pitchStats.setFrequency(frequency);
        return pitchStats;
    }

    // GETTERS

    public String getId() {
        return id;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public String getFrequency() {
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
    public void setId(String id) {
        this.id = id;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setFrequency(String frequency) {
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
