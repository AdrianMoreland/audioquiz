package com.audioquiz.data.local.dao.quiz;

import androidx.room.Dao;
import androidx.room.Query;

import com.audioquiz.data.local.dao.BaseDao;
import com.audioquiz.data.local.entity.QuestionEntity;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface QuestionDao extends BaseDao<QuestionEntity> {
    @Query("SELECT * FROM questions")
    QuestionEntity getAllQuestions();

    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 1")
    QuestionEntity getQuestion();

    @Query("SELECT * FROM questions WHERE category = :category ORDER BY RANDOM() LIMIT 1")
    QuestionEntity  getQuestionByCategory(String category);

    @Query("SELECT * FROM questions WHERE category = :category AND chapter = :chapter ORDER BY RANDOM() LIMIT 1")
    QuestionEntity  getQuestionByCategoryAndChapter(String category, int chapter);

    @Query("SELECT * FROM questions WHERE id = :id")
    QuestionEntity  getQuestionById(int id);

    @Query("DELETE FROM questions")
    void deleteAll();

    // Add this new method
    @Query("SELECT * FROM questions ORDER BY RANDOM() LIMIT 1")
    Single<QuestionEntity> getQuestionSingle(); // Assuming Room supports Single variants
}