package com.audioquiz.core.model.quiz;


/**
 * Represents the state of the quiz.
 * [userScore] The user's score.
 * [questionCounter] The number of questions answered.
 * [lives] The number of lives the user has left.
 */
public class QuizState {
    private int userScore;
    private int questionCounter;
    private int lives;

    // Constructor, getters, and setters
    public QuizState(int userScore, int questionCounter, int lives) {
        this.userScore = userScore;
        this.questionCounter = questionCounter;
        this.lives = lives;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public int getQuestionCounter() {
        return questionCounter;
    }

    public void setQuestionCounter(int questionCounter) {
        this.questionCounter = questionCounter;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}

