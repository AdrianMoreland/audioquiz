package com.audioquiz.data.local.entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Map;

// Database Entity for a quiz result
@Entity(tableName = "quiz_result_table")
public class QuizResultEntity {
        @PrimaryKey(autoGenerate = true)
        public int id;
        public String lives;
        public String category;
        public int chapter;
        public String quizType;
        public int score;
        public String rootNote;
        public int maxQuestions;
        public int questionCounter;
        public Map<String, Integer> scorePerCategory;
        public Map<String, Integer> scorePerFrequency;
        public Map<String, Object> quizTimeData;

        // Getters and Setters
        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public String getLives() {
                return lives;
        }

        public void setLives(String lives) {
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


        // Usable public constructor
        public QuizResultEntity() {
                // Initialize fields here
        }
}
