package com.audioquiz.core.model.quiz;

/**
 * Represents a question in the quiz.
 * [id] The unique identifier of the question.
 * [category] The category of the question.
 * [chapter] The chapter of the question.
 * [level] The difficulty level of the question.
 * [type] The type of the question.
 * [questionText] The text of the question.
 * [option1], [option2], [option3], [option4] The options of the question.
 * [answerNr] The number of the correct answer.
 * [explanation] The explanation of the correct answer.
 */
public class Question {
    private int id;
    private String category;
    private int chapter;
    private int level;
    private String type;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNr;
    private String explanation;

    // GETTERS AND SETTERS

    // SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
    public void setOption1(String option1) {
        this.option1 = option1;
    }
    public void setOption2(String option2) {
        this.option2 = option2;
    }
    public void setOption3(String option3) {
        this.option3 = option3;
    }
    public void setOption4(String option4) {
        this.option4 = option4;
    }
    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    // GETTERS
    public int getId() {
        return id;
    }
    public int getChapter() {
        return chapter;
    }
    public int getLevel() {
        return this.level;
    }
    public String getType() {
        return this.type;
    }
    public String getQuestionText() {
        return this.questionText;
    }
    public String getCategory() {
        return this.category;
    }
    public String getOption1() {
        return this.option1;
    }
    public String getOption2() {
        return this.option2;
    }
    public String getOption4() {
        return this.option4;
    }
    public String getOption3() {
        return this.option3;
    }
    public int getAnswerNr() {
        return this.answerNr;
    }
    public String getExplanation() {
        return explanation;
    }
    public String getCorrectOption() {
        switch (this.answerNr + 1) {
            case 1:
                return this.option1;
            case 2:
                return this.option2;
            case 3:
                return this.option3;
            case 4:
                return this.option4;
            default:
                return null;
        }
    }
}
