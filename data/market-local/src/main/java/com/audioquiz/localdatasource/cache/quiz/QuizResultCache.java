package com.audioquiz.localdatasource.cache.quiz;

import com.adrian.api.data.datasources.quiz_result.QuizLocal;
import com.adrian.database.mapper.IDatabaseMapper;
import com.adrian.database.dao.quiz.QuizResultDao;
import com.adrian.database.entity.QuizResultEntity;
import com.adrian.database.provider.AppDatabase;
import com.adrian.model.quiz.QuizResult;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuizResultCache implements QuizLocal {
    private final QuizResultDao quizResultDao;
    private final IDatabaseMapper mapper;

    @Inject
    public QuizResultCache(AppDatabase appDatabase,
                           IDatabaseMapper mapper) {
        this.quizResultDao = appDatabase.quizResultDao();
        this.mapper = mapper;
    }

    public Single<QuizResult> getQuizResult() {
        return quizResultDao.getQuizResultSingle()
                .subscribeOn(Schedulers.io()) // Perform database access on IO thread
                .map(this::mapToDomain) // Use map with separate transformation function
                .observeOn(AndroidSchedulers.mainThread()); // Observe on main thread (if transformation is light)
    }

    public void insert(QuizResult quizResult) {
        quizResultDao.insert(mapToEntity(quizResult));
    }

/*    private QuizResultEntity mapToEntity(QuizResult quizResult) {
        return EntityMapper.mapToSource(quizResult, QuizResultEntity.class);
    }

    private QuizResult mapToDomain(QuizResultEntity quizResultEntity) {
        return EntityMapper.mapFromSource(quizResultEntity, QuizResult.class);
    }*/

    private QuizResultEntity mapToEntity(QuizResult domain) {
        return mapper.mapToDomain(domain, QuizResultEntity.class);
    }

    private QuizResult mapToDomain(QuizResultEntity entity) {
        return mapper.mapToDomain(entity, QuizResult.class);
    }
}