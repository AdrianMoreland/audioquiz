package com.audioquiz.localdatasource.dao.quiz;


import com.adrian.database.dao.BaseDao;
import com.adrian.database.entity.QuestionEntity;
import java.util.List;

public interface IQuestionDao extends BaseDao<QuestionEntity> {
    List<QuestionEntity> getAllQuestions();
    QuestionEntity getQuestion();
    QuestionEntity getQuestionByCategory(String category);
    QuestionEntity getQuestionByCategoryAndChapter(String category, int chapter);
    QuestionEntity getQuestionById(int id);
    void deleteAll();
}
