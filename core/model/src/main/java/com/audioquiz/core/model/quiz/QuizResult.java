package com.audioquiz.core.model.quiz;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents the result of a quiz taken by the user.
 * [id] represents the identifier of the quiz result.
 * [lives] represents the number of lives the user has left.
 * [category] represents the category of the quiz.
 * [chapter] represents the chapter of the quiz.
 * [quizType] represents the type of quiz.
 * [score] represents the score of the quiz.
 * [rootNote] represents the root note of the quiz.
 * [maxQuestions] represents the maximum number of questions in the quiz.
 * [questionCounter] represents the number of questions answered in the quiz.
 * [scorePerCategory] represents the score per category in the quiz.
 * [scorePerFrequency] represents the score per frequency in the quiz.
 * [quizTimeData] represents the time data of the quiz.
 */
public class QuizResult {
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

    public QuizResult() {}

    public static QuizResult createDefault() {
        Map<String, Integer> defaultScorePerCategory = createDefaultScorePerCategory();
        Map<String, Object> defaultQuizTimeData = createDefaultQuizTimeData();

        return new QuizResult(defaultQuizTimeData, 0, 0, 0,
                0, "N/A", "N/A", 1, "N/A", defaultScorePerCategory, new HashMap<>());
    }

    private static Map<String, Object> createDefaultQuizTimeData() {
        Date quizDate = new Date();
        long startTime = System.currentTimeMillis();

        Map<String, Object> quizTimeData = new HashMap<>();
        quizTimeData.put("quizDate", quizDate);
        quizTimeData.put("duration", 0);
        quizTimeData.put("startTime", startTime);
        quizTimeData.put("endTime", 0);
        return quizTimeData;
    }

    private static Map<String, Integer> createDefaultScorePerCategory() {
        Map<String, Integer> defaultScorePerCategory = new HashMap<>();
        defaultScorePerCategory.put("SoundWaves", 0);
        defaultScorePerCategory.put("Synthesis", 0);
        defaultScorePerCategory.put("Production", 0);
        defaultScorePerCategory.put("Mixing", 0);
        defaultScorePerCategory.put("Processing", 0);
        defaultScorePerCategory.put("MusicTheory", 0);
        defaultScorePerCategory.put("Pitch", 0);
        defaultScorePerCategory.put("Interval", 0);
        return defaultScorePerCategory;
    }


    public QuizResult(Map<String, Object> quizTimeData, int maxQuestions, int questionCounter,
                            int score, int lives, String rootNote, String category, int chapter, String quizType,
                            Map<String, Integer> scorePerCategory, Map<String, Integer> scorePerFrequency) {

        this.quizTimeData = quizTimeData;
        this.maxQuestions = maxQuestions;
        this.questionCounter = questionCounter;
        this.score = score;
        this.lives = lives;
        this.rootNote = rootNote;
        this.category = category;
        this.chapter = chapter;
        this.quizType = quizType;
        this.scorePerCategory = scorePerCategory;
        this.scorePerFrequency = scorePerFrequency;
    }

    // Getter methods
    public int getId() {
        return id;
    }
    public int getScore() {
        return score;
    }
    public int getLives() {
        return lives;
    }
    public String getCategory() {
        return category;
    }
    public int getChapter() {
        return chapter;
    }
    public String getQuizType() {
        return quizType;
    }
    public String getRootNote() {
        return rootNote;
    }
    public int getMaxQuestions() {
        return maxQuestions;
    }
    public int getQuestionCounter() {
        return questionCounter;
    }
    public Map<String, Object> getQuizTimeData() {
        return quizTimeData;
    }
    public Map<String, Integer> getScorePerCategory() {
        return scorePerCategory;
    }
    public Map<String, Integer> getScorePerFrequency() {
        return scorePerFrequency;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }
    public void setRootNote(String rootNote) {
        this.rootNote = rootNote;
    }
    public void setMaxQuestions(int maxQuestions) {
        this.maxQuestions = maxQuestions;
    }
    public void setQuestionCounter(int questionCounter) {
        this.questionCounter = questionCounter;
    }
    public void setQuizTimeData(Map<String, Object> quizTimeData) {
        this.quizTimeData = quizTimeData;
    }
    public void setScorePerCategory(Map<String, Integer> scorePerCategory) {
        this.scorePerCategory = scorePerCategory;
    }
    public void setScorePerFrequency(Map<String, Integer> scorePerFrequency) {
        this.scorePerFrequency = scorePerFrequency;
    }
}
