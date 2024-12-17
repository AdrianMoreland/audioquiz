package com.audioquiz.sync.worker;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.hilt.work.HiltWorker;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.audioquiz.core.domain.repository.quiz.QuestionRepository;
import com.audioquiz.core.model.quiz.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dagger.assisted.Assisted;
import dagger.assisted.AssistedInject;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltWorker
public class RxQuestionWorker extends RxWorker {
    private static final String TAG = "RxQuestionWorker";
      private static final int BUFFER_SIZE = 16 * 1024; // 16KB buffer size
    private static final long DEFAULT_MIN_INTERVAL = 15L;

    private final QuestionRepository questionRepository;
    private final Gson gson;

    @AssistedInject
    public RxQuestionWorker(
            @Assisted @NonNull Context appContext,
            @Assisted @NonNull WorkerParameters workerParams,
            QuestionRepository questionRepository,
            Gson gson
    ) {
        super(appContext, workerParams);
        this.questionRepository = questionRepository;
        this.gson = gson;
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        return Single.fromCallable(() -> {
                    Log.d(TAG, "createWork: starting my work");
                    try {
                        // Read JSON from assets
                        String questionsJson = "";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            // Read JSON from assets
                            questionsJson = readJsonFromAssets();
                        }
                        // Parse the JSON to a list of Question objects
                        List<Question> questions = parseJson(questionsJson);
                        // Insert questions into the database
                        questionRepository.insertQuestions(questions);
                        // Return success
                        return Result.success();
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading or parsing JSON", e);
                        return Result.failure();
                    }
                })
                .subscribeOn(Schedulers.io()) // Ensure this runs on the I/O thread
                .onErrorReturnItem(Result.failure()); // Handle any errors
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private String readJsonFromAssets() throws IOException {
        try (InputStream is = getApplicationContext().getAssets().open("questions.json");
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[BUFFER_SIZE];
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toString(StandardCharsets.UTF_8);
        }
    }

    private List<Question> parseJson(String jsonString) {
        Type listType = new TypeToken<List<Question>>() {}.getType();
        return gson.fromJson(jsonString, listType);
    }

    public static OneTimeWorkRequest oneTimeWorkRequest() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        return new OneTimeWorkRequest.Builder(SyncQuestionDataWorker.class)
                .setConstraints(constraints)
                .addTag("question_work_tag")
                .build();
    }

    public static PeriodicWorkRequest periodicWorkRequest() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        return new PeriodicWorkRequest.Builder(SyncQuestionDataWorker.class, DEFAULT_MIN_INTERVAL, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
    }

}