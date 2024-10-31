package com.audioquiz.data.local.dao.quiz;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.audioquiz.data.local.entity.QuizResultEntity;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface QuizResultDao {

    @Query("SELECT * FROM quiz_result_table LIMIT 1")
    Single<QuizResultEntity> getQuizResultSingle();

    @Insert
    void insert(QuizResultEntity quizResultEntity);
}
