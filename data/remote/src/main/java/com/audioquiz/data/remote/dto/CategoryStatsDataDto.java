package com.audioquiz.data.remote.dto;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class CategoryStatsDataDto {
    @ServerTimestamp
    public Date lastUpdated;
    public int categoryIndex;
    public String categoryName;
    public int currentChapter;
    public int numberOfQuizzes;
    public int totalQuestionsLearn;
    public int correctAnswersLearn;
    public int totalQuestionsCompetitive;
    public int correctAnswersCompetitive;
    public double totalTimeSpent;

    public CategoryStatsDataDto() {
        // Required empty public constructor
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }


    @NonNull
    @Override
    public String toString() {
        return "com.example.model.CategoryStatsDataDto{" +
                "categoryIndex=" + categoryIndex +
                "categoryName=" + categoryName +
                "currentChapter=" + currentChapter +
                ", numberOfQuizzes=" + numberOfQuizzes +
                ", totalQuestionsLearn=" + totalQuestionsLearn +
                ", correctAnswersLearn=" + correctAnswersLearn +
                ", totalQuestionsCompetitive=" + totalQuestionsCompetitive +
                ", correctAnswersCompetitive=" + correctAnswersCompetitive +
                ", totalTimeSpent=" + totalTimeSpent +
                '}';
    }
}