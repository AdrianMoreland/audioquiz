package com.audioquiz.api.datasources.question;


import com.audioquiz.core.model.quiz.Question;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface QuestionDataSource {
    interface Local {
        Question getQuestion ();
        Question getQuestionByCategory(String category);
        Question getQuestionByCategoryAndChapter(String category, int chapter);
        Question getQuestionById(int id);
        void insert(Question question);
        Single<Question> getQuestionSingle();
        Single<Question> getQuestionByCategoryRx(String category);
    }

    interface Remote {
        List<Question> getQuestions();
        Question getQuestion ();
        Question getQuestionByCategory(String category);
        Question getQuestionByCategoryAndChapter(String category, int chapter);
        Question getQuestionById(int id);
        void insert(Question questionEntity);
    }
}