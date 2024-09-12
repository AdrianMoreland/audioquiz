package com.adrian.model.stats;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the statistics of a user per frequency.
 * [frequency] The base frequency of the interval.
 * [totalQuestions] The total number of questions answered by the user.
 * [correctAnswers] The total number of correct answers given by the user.
 * [mistakes] The mistakes made by the user with
 *     String = interval name of the correct option
 * and
 *     Integer = number of mistakes with that interval.
 */
public class IntervalStats {
    private String id;
    private Date lastUpdated;
    private String intervalName;
    private int baseFrequency;
    private int totalQuestions;
    private int correctAnswers;
    private Map<String, Integer> mistakes;

    private IntervalStats() {
        this.intervalName = "";
        this.baseFrequency = 0;
        this.totalQuestions = 0;
        this.correctAnswers = 0;
        this.mistakes = new HashMap<>();
    }

    public static IntervalStats createDefault(String intervalName, int frequency) {
        IntervalStats intervalStats = new IntervalStats();
        intervalStats.setIntervalName(intervalName);
        intervalStats.setBaseFrequency(frequency);
        return intervalStats;
    }

    // GETTERS
    public String getId() {
        return id;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public String getIntervalName() {
        return intervalName;
    }
    public int getBaseFrequency() {
        return baseFrequency;
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
    public void setIntervalName(String intervalName) {
        this.intervalName = intervalName;
    }
    public void setBaseFrequency(int baseFrequency) {
        this.baseFrequency = baseFrequency;
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
