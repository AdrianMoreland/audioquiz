package com.audioquiz.localdatasource.cache.quiz;

import com.adrian.api.data.datasources.question.QuestionLocal;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.provider.AppDatabase;
import com.adrian.database.dao.quiz.QuestionDao;
import com.adrian.database.entity.QuestionEntity;
import com.adrian.model.quiz.Question;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class QuestionsCache implements QuestionLocal {
    private final QuestionDao questionDao;
    private final IDatabaseMapper mapper;

    @Inject
    public QuestionsCache(AppDatabase appDatabase,
                          IDatabaseMapper mapper) {
        this.questionDao = appDatabase.questionDao();
        this.mapper = mapper;
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
                .map(questionEntity -> mapToDomain(questionEntity));
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

/*    private QuestionEntity mapToEntity(Question question) {
        return EntityMapper.mapToSource(question, QuestionEntity.class);
    }

    private Question mapToDomain(QuestionEntity questionEntity) {
        return EntityMapper.mapFromSource(questionEntity, Question.class);
    }*/


    private Question mapToDomain (QuestionEntity entity) {
        return mapper.mapToDomain(entity, Question.class);
    }

    private QuestionEntity mapToEntity (Question domain) {
        return mapper.mapToDomain(domain, QuestionEntity.class);
    }
}