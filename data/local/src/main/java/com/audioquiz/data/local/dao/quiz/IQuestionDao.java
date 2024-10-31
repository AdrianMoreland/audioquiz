package com.audioquiz.data.local.dao.quiz;


import com.audioquiz.data.local.dao.BaseDao;
import com.audioquiz.data.local.entity.QuestionEntity;
import java.util.List;

public interface IQuestionDao extends BaseDao<QuestionEntity> {
    List<QuestionEntity> getAllQuestions();
    QuestionEntity getQuestion();
    QuestionEntity getQuestionByCategory(String category);
    QuestionEntity getQuestionByCategoryAndChapter(String category, int chapter);
    QuestionEntity getQuestionById(int id);
    void deleteAll();
}
