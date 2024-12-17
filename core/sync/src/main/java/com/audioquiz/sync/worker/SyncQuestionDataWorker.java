package com.audioquiz.sync.worker;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.work.HiltWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.audioquiz.core.domain.usecase.quiz.question.QuestionUseCaseFacade;
import com.audioquiz.core.model.quiz.Question;
import com.audioquiz.sync.initializer.DatabaseInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Named;

import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltWorker
public class SyncQuestionDataWorker extends Worker {
    public static final String TAG = "SyncQuestionDataWorker";
    private final QuestionUseCaseFacade usecase;
    private final Scheduler ioScheduler;
    private final ExecutorService executorService;
    private Disposable disposable;

    @AssistedInject
    public SyncQuestionDataWorker(@NonNull Context context,
                                  @NonNull WorkerParameters workerParams,
                                  QuestionUseCaseFacade usecase,
                                  Scheduler ioScheduler,
                                  @Named("SingleThreadExecutor") ExecutorService executorService) {
        super(context, workerParams);
        this.usecase = usecase;
        this.ioScheduler = ioScheduler;
        this.executorService = executorService;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: SyncQuestionDataWorker");
        try {
            DatabaseInitializer databaseInitializer = new DatabaseInitializer();

            // Get the InputStream from assets
            String jsonString;
            try (InputStream inputStream = getApplicationContext().getAssets().open("questions.json")) {
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                int bytesRead = inputStream.read(buffer);
                if (bytesRead != size) {
                    throw new IOException("Failed to read the entire file");
                }
                jsonString = new String(buffer, StandardCharsets.UTF_8);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                disposable = Single.fromCallable(() -> parseJson(jsonString))
                        .subscribeOn(Schedulers.from(executorService))
                        .observeOn(ioScheduler)
                        .subscribe(questions -> usecase.insertAllQuestions(questions.stream()
                                .map(question -> new Question())
                                .toList()
                        ), error -> Log.e("SyncQuestionDataWorker", "Error parsing or inserting questions", error));
            }

            return Result.success();
        } catch (Exception e) {
            Log.e("SyncQuestionDataWorker", "Error loading or parsing questions", e);
            return Result.failure();
        }
    }

    private List<Question> parseJson(String jsonString) {
        Log.d(TAG, "parseJson: " + jsonString);
        List<Question> questions;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Question>>() {
            }.getType();
            questions = gson.fromJson(jsonString, listType);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                return questions.stream()
                        .toList();
            }
        } catch (JsonSyntaxException e) {
            Log.e(TAG, "Error parsing JSON", e);
            return new ArrayList<>();
        }
        return questions;
    }


    @Override
    public void onStopped() {
        super.onStopped();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        executorService.shutdown();
    }
}