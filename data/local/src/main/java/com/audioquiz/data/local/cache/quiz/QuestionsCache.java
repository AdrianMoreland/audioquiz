package com.audioquiz.data.local.cache.quiz;

import com.audioquiz.api.datasources.question.QuestionLocal;
import com.audioquiz.core.model.quiz.Question;
import com.audioquiz.data.local.dao.quiz.QuestionDao;
import com.audioquiz.data.local.entity.QuestionEntity;
import com.audioquiz.data.local.mapper.DatabaseMapper;
import com.audioquiz.data.local.provider.AppDatabase;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class QuestionsCache implements QuestionLocal {
    private final QuestionDao questionDao;

    @Inject
    public QuestionsCache(AppDatabase appDatabase) {
        this.questionDao = appDatabase.questionDao();
    }

    @Override
    public Question getQuestion( ) {
        QuestionEntity questionEntity = questionDao.getQuestion();
        return mapToDomain(questionEntity);
    }
    public Single<Question> getQuestionSingle() {
        return Single.create(emitter -> {
            QuestionEntity questionEntity = questionDao.getQuestion();
            emitter.onSuccess(mapToDomain(questionEntity));
        });
    }

    // Optional: Implement this method if needed for specific use cases
    public Single<Question> getQuestionByCategoryRx(String category) {
        // Implement logic to fetch question by category using Room with RxJava (if applicable)
        // Wrap the Room call in a Single and return
        return Single.fromCallable(() -> questionDao.getQuestionByCategory(category))
                .map(this::mapToDomain);
    }


    @Override
    public Question getQuestionByCategory(String category) {
        QuestionEntity questionEntity =  questionDao.getQuestionByCategory(category);
        return mapToDomain(questionEntity);
    }

    @Override
    public Question getQuestionByCategoryAndChapter(String category, int chapter) {
        QuestionEntity questionEntity =  questionDao.getQuestionByCategoryAndChapter(category, chapter);
        return mapToDomain(questionEntity);
    }

    @Override
    public Question getQuestionById(int id) {
        QuestionEntity questionEntity =  questionDao.getQuestionById(id);
        return mapToDomain(questionEntity);
    }

    @Override
    public void insert(Question question) {
        questionDao.insert(mapToEntity(question));
    }


    private Question mapToDomain (QuestionEntity entity) {
        return DatabaseMapper.map(entity, Question.class);
    }

    private QuestionEntity mapToEntity (Question domain) {
        return DatabaseMapper.map(domain, QuestionEntity.class);
    }
}