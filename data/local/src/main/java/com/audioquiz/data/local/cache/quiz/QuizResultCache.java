package com.audioquiz.data.local.cache.quiz;

import com.audioquiz.api.datasources.quiz_result.QuizLocal;
import com.audioquiz.core.model.quiz.QuizResult;
import com.audioquiz.data.local.dao.quiz.QuizResultDao;
import com.audioquiz.data.local.entity.QuizResultEntity;
import com.audioquiz.data.local.mapper.DatabaseMapper;
import com.audioquiz.data.local.provider.AppDatabase;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuizResultCache implements QuizLocal {
    private final QuizResultDao quizResultDao;

    @Inject
    public QuizResultCache(AppDatabase appDatabase) {
        this.quizResultDao = appDatabase.quizResultDao();
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


    private QuizResultEntity mapToEntity(QuizResult domain) {
        return DatabaseMapper.map(domain, QuizResultEntity.class);
    }

    private QuizResult mapToDomain(QuizResultEntity entity) {
        return DatabaseMapper.map(entity, QuizResult.class);
    }
}