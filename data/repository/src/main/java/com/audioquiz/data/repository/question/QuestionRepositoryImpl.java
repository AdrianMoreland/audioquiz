package com.audioquiz.data.repository.question;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.audioquiz.api.datasources.question.QuestionDataSource;
import com.audioquiz.core.domain.repository.quiz.QuestionRepository;
import com.audioquiz.core.model.quiz.Question;
import com.audioquiz.data.util.mapper.QuestionJsonMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleSource;


public class QuestionRepositoryImpl implements QuestionRepository {
    private static final String TAG = "QuestionRepositoryImpl";
    private final QuestionDataSource.Local questionLocal;
    private final QuestionDataSource.Remote questionApi;
    private final QuestionJsonMapper questionJsonMapper;

    private String rootNote = "C";
    private Map<Integer, String> intervalButtonsIndex = new HashMap<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final Random random = new Random();

    private static final int[] FREQUENCIES = {
            80, 90, 100, 200, 300, 400, 500, 600, 700, 800, 900,
            1000, 1250, 1500, 1750, 2000, 2500, 3000, 3500, 4000,
            4500, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000,
            13000, 14000, 15000, 16000
    };

    @Inject
    public QuestionRepositoryImpl(QuestionDataSource.Local questionLocal,
                                  QuestionDataSource.Remote questionApi,
                                  QuestionJsonMapper questionJsonMapper) {
        this.questionLocal = questionLocal;
        this.questionApi = questionApi;
        this.questionJsonMapper = questionJsonMapper;
        MutableLiveData<Question> currentQuestionLiveData = new MutableLiveData<>();
   //     setIntervalButtonsIndex(MusicData.INTERVAL_BUTTONS);
    }

    public void init() {
        Log.d(TAG, "init: ");
        preloadQuestionsFromJsonOnLaunch(null, null);
    }


    @Override
    public void insertQuestions(List<Question> questions) {
        for (Question question : questions) {
            questionLocal.insert(question);
        }
    }


    @Override
    public void preloadQuestionsFromJsonOnLaunch(InputStream inputStream, Runnable onInputStreamClosed) {
        if (inputStream != null) {
            PreloadQuestionsRunnable runnable = new PreloadQuestionsRunnable(this, questionLocal, inputStream, onInputStreamClosed);
            executorService.execute(runnable);
        } else {
            Log.e(TAG, "InputStream is null");
        }
    }

    private static class PreloadQuestionsRunnable implements Runnable {
        private final QuestionRepositoryImpl questionRepository;
        private final QuestionDataSource.Local questionLocal;
        private final InputStream inputStream;
        private final Runnable onInputStreamClosed;

        public PreloadQuestionsRunnable(QuestionRepositoryImpl questionRepository,
                                        QuestionDataSource.Local questionLocal,
                                        InputStream inputStream,
                                        Runnable onInputStreamClosed) {
            this.questionRepository = questionRepository;
            this.questionLocal = questionLocal;
            this.inputStream = inputStream;
            this.onInputStreamClosed = onInputStreamClosed;
        }

        public void run() {
            try {
                InputStream input = this.inputStream;

                // Convert InputStream to JSONArray
                int size = input.available(); // Get the size of the input stream
                byte[] buffer = new byte[size]; // Create a buffer to store the input stream
                int bytesRead = input.read(buffer);   // Read the input stream into the buffer

                if (bytesRead != size) { // Check if the number of bytes read is equal to the size
                    Log.w(TAG, "Expected " + size + " bytes, but read " + bytesRead + " bytes");
                }

                String json = new String(buffer, StandardCharsets.UTF_8);   // Convert the buffer to a string
                JSONArray jsonArray = new JSONArray(json); // Create a JSON array from the string

                // Process each JSON object and add it to the cache
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Question question = questionRepository.getQuestionFromJson(jsonObject);
                    questionLocal.insert(question);
                }
                Log.d(TAG, "Added " + jsonArray.length() + " questions to cache");
            } catch (IOException | JSONException e) {
                Log.e(TAG, "Error loading questions from JSON", e);
            } finally {
                try {
                    inputStream.close();
                    onInputStreamClosed.run(); // Run the provided Runnable
                } catch (IOException e) {
                    Log.e(TAG, "Error closing InputStream", e);
                }
            }
        }
    }

    public Question getQuestionFromJson(JSONObject jsonObject) throws JSONException {
        return QuestionJsonMapper.mapFromJson(jsonObject);
    }

    // LOADING_QUIZ_QUESTIONS_TO_UI
    // --------------------------------------------------------------------------------------------
     @Override
    public Question getQuestion(String category, int chapter) {
        Question question;

        if (category.equalsIgnoreCase("all") && chapter == 0) {
            question = questionLocal.getQuestion();
        } else if (chapter >= 4) {
            question = questionLocal.getQuestionByCategory(category);
        } else {
            question = questionLocal.getQuestionByCategoryAndChapter(category, chapter);
        }
        return question;
    }


    @Override
    public Single<Question> getQuestionSingle(String category, int chapter) {
        if (category.equalsIgnoreCase("all") && chapter == 0) {
            return questionLocal.getQuestionSingle(); // Use getQuestionSingle() method from QuestionCache
        } else if (chapter >= 4) {
            return questionLocal.getQuestionByCategoryRx(category); // Assuming getQuestionByCategoryRx is implemented in QuestionCache
        } else {
            return Single.fromCallable(() -> questionLocal.getQuestionByCategoryAndChapter(category, chapter))
                    .flatMap(question -> question == null ? fetchQuestionFromFirestore(category, chapter) : Single.just(question));
        }
    }

    private SingleSource<? extends Question> fetchQuestionFromFirestore(String category, int chapter) {
        // Implement logic to fetch question from Firestore using Firestore SDK
        // Convert fetched data to Question and return a Single
        return Single.create(emitter -> {
            // Your Firestore data fetching logic here
            // Convert data to Question and emit it
            emitter.onError(new Throwable("Firestore implementation not provided"));
        });
    }


    @Override
    public int getCorrectAnswerNr(int questionId) {
        return 0;
    }

    public void setIntervalButtonsIndex(Map<Integer, String> intervalButtonsIndex) {
        this.intervalButtonsIndex = intervalButtonsIndex;
    }

    public Map<Integer, String> getIntervalButtonsIndex() {
        return intervalButtonsIndex;
    }

    public void setQuizSelection(String category, int chapter, String quizType) {
        Log.d(TAG, "setQuizSelection arguments received: Category = " + category + ", Chapter = " + chapter + ", QuizType = " + quizType);
        String quizType1;
        if (quizType.equals("pitch") || quizType.equals("lesson") || quizType.equals("all")) {
            quizType1 = quizType;
        } else {
            setRootNote(quizType);
            quizType1 = "intervals";
        }
        Log.d(TAG, "setQuizSelection Livedata values: Category = " + category + ", Chapter = " + chapter + ", QuizType = " + quizType);
    }

    @Override
    public Question getCurrentQuestion() {
            return null;
    }

    public void setRootNote(String quizRootNote) {
        this.rootNote = quizRootNote;
    }

    public String getRootNote() {
        return rootNote;
    }

}
