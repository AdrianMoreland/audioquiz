package com.audioquiz.data.local.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Database DTO for a question
@Entity(tableName = "questions")
public class QuestionEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "chapter")
    public int chapter;

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "question_text")
    public String questionText;

    @ColumnInfo(name = "option1")
    public String option1;

    @ColumnInfo(name = "option2")
    public String option2;

    @ColumnInfo(name = "option3")
    public String option3;

    @ColumnInfo(name = "option4")
    public String option4;

    @ColumnInfo(name = "answer_nr")
    public int answerNr;

    @ColumnInfo(name = "explanation")
    public String explanation;


}