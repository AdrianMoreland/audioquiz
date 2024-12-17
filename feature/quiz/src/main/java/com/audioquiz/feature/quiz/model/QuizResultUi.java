package com.audioquiz.feature.quiz.model;

import java.util.Map;

public class QuizResultUi {
    private int id;
    private int lives;
    private String category;
    private int chapter;
    private String quizType;
    private int score;
    private String rootNote;
    private int maxQuestions;
    private int questionCounter;
    private Map<String, Integer> scorePerCategory;
    private Map<String, Integer> scorePerFrequency;
    private Map<String, Object> quizTimeData;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getRootNote() {
        return rootNote;
    }

    public void setRootNote(String rootNote) {
        this.rootNote = rootNote;
    }

    public int getMaxQuestions() {
        return maxQuestions;
    }

    public void setMaxQuestions(int maxQuestions) {
        this.maxQuestions = maxQuestions;
    }

    public int getQuestionCounter() {
        return questionCounter;
    }

    public void setQuestionCounter(int questionCounter) {
        this.questionCounter = questionCounter;
    }

    public Map<String, Integer> getScorePerCategory() {
        return scorePerCategory;
    }

    public void setScorePerCategory(Map<String, Integer> scorePerCategory) {
        this.scorePerCategory = scorePerCategory;
    }

    public Map<String, Integer> getScorePerFrequency() {
        return scorePerFrequency;
    }

    public void setScorePerFrequency(Map<String, Integer> scorePerFrequency) {
        this.scorePerFrequency = scorePerFrequency;
    }

    public Map<String, Object> getQuizTimeData() {
        return quizTimeData;
    }

    public void setQuizTimeData(Map<String, Object> quizTimeData) {
        this.quizTimeData = quizTimeData;
    }

}