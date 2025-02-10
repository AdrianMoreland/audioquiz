package com.audioquiz.data.repository.quiz;

import android.util.Log;

import com.audioquiz.api.datasources.quiz_result.QuizLocal;
import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.model.quiz.Question;
import com.audioquiz.core.model.quiz.QuizResult;
import com.audioquiz.library.util.StringExtensions;

import java.util.Map;

import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


@InstallIn(SingletonComponent.class)
@Module
public class QuizRepository_impl implements QuizRepository {
    private static final String TAG = "QuizRepository_Impl";
    private QuizResult quizResult;
    private final QuizLocal quizLocal;
    private Boolean isQuizOver = false;
    private Boolean isLastQuestionLiveData = false;
    private Boolean isCorrectLiveData = false;


    @Inject
    public QuizRepository_impl(QuizLocal quizLocal) {
        this.quizLocal = quizLocal;
      //  this.quizResultLiveData = new MutableLiveData<>();
    }

    public void initQuizResult() {
        quizResult = QuizResult.createDefault();
    }

    @Override
    public QuizResult getQuizResult() {
        if (quizResult == null) {
            initQuizResult();
        }
        return quizResult;
    }

    @Override
    public Single<QuizResult> getQuizResultSingle() {
        return quizLocal.getQuizResult();
    }

    @Override
    public Observable<Boolean> getIsQuizEndedObservable() {
        return null;
    }

    @Override
    public Observable<Boolean> getIsLastQuestionObservable() {
        Observable<Boolean> isLastQuestionObservable;
        isLastQuestionObservable = Observable.create(emitter -> {
            emitter.onNext(isLastQuestionLiveData);
        });
        return isLastQuestionObservable;
    }

    @Override
    public Observable<Boolean> getIsCorrectObservable() {
        return null;
    }
    @Override
    public Integer getUserScore() {
        return 0;
    }
    @Override
    public String getCategory() {
        return getQuizResult().getCategory();
    }
    @Override
    public int getChapter() {
        return getQuizResult().getChapter();
    }
    @Override
    public String getQuizType() {
        return getQuizResult().getQuizType();
    }

    @Override
    public void setIsQuizOver(boolean isQuizOver) {
        this.isQuizOver = isQuizOver;
    }
    @Override
    public void setIsLastQuestion(boolean isLastQuestion) {
        this.isLastQuestionLiveData = isLastQuestion;
    }

    @Override
    public Boolean getIsLastQuestion() {
        return isLastQuestionLiveData;
    }

    @Override
    public Integer getCorrectOption2() {
            return 0;
    }

    @Override
    public Integer getCorrectOption1() {
        return 0;
    }

    @Override
    public QuizResult getQuizResultLiveData() {
        return quizResult;
    }

    @Override
    public String getRootNote() {
        return quizResult.getRootNote();
    }
    @Override
    public String getCurrentLives() {
        return StringExtensions.getStringValue(quizResult.getLives());
    }

    @Override
    public int getQuestionCount() {
        return quizResult.getQuestionCounter();
    }

    @Override
    public int getMaxQuestions() {
        return quizResult.getMaxQuestions();
    }
    @Override
    public Boolean getIsCorrect() {
        return isCorrectLiveData;
    }
    @Override
    public Boolean getIsQuizEndedLiveData() {
        return isQuizOver;
    }


    @Override
    public void updateIsCorrect(boolean isCorrect) {
        this.isCorrectLiveData = isCorrect;
        Log.d(TAG, "IsCorrect: " + isCorrect);
    }
    @Override
    public void updateUserScore() {
        quizResult.setScore(quizResult.getScore() + 1);
        Log.d(TAG, "Updated quiz score: " + quizResult.getScore());
    }
    @Override
    public void updateLives() {
        int currentUserLives = quizResult.getLives();

        if  (currentUserLives > 0) {
            quizResult.setLives(currentUserLives -1);
            Log.d(TAG, "Lives decremented. Current number of lives: " + quizResult.getLives());
        } else {
            Log.d("QuestionViewModel", "User has no more lives.");
        }
    }
    @Override
    public void updateQuestionCount() {
        int currentQuestionCount = getQuizResult().getQuestionCounter();
        getQuizResult().setQuestionCounter(currentQuestionCount + 1);
        Log.d(TAG, "Updated question counte: " + quizResult.getQuestionCounter());

    }
    @Override
    public void updateScorePerCategory(String category) {
        Map<String, Integer> scorePerCategory = getQuizResult().getScorePerCategory();

        // Check if category exists in the map
        if (scorePerCategory.containsKey(category)) {
            Integer currentScore = scorePerCategory.get(category);
            if (currentScore == null) {
                currentScore = 0;
            }
            scorePerCategory.put(category, currentScore + 1);
        } else {
            scorePerCategory.put(category, 1);
        }
        Log.d(TAG, "Update score per category: " + category + " in Map: " + getQuizResult().getScorePerCategory());
    }

    @Override
    public void updateScorePerFrequency(boolean isCorrect, Question question, String selectedFrequency) {
        Map<String, Integer> scorePerFrequency = getQuizResult().getScorePerFrequency();
        scorePerFrequency.put(question.getCorrectOption(), Integer.parseInt(selectedFrequency));
        Log.d(TAG, "Update score per frequency: " + question.getCorrectOption() + " in Map: " + getQuizResult().getScorePerFrequency());
    }

    @Override
    public void updateQuizTimeData(Map<String, Object> quizTimeData) {
        getQuizResult().setQuizTimeData(quizTimeData);
    }




        /*
    @Override
    public void updateScorePerCategory(String category) {
        Map<String, Integer> scorePerCategory = getQuizResult().getScorePerCategory();

        // Check if category exists in the map
        if (scorePerCategory.containsKey(category)) {
            Integer currentScore = scorePerCategory.get(category);
            if (currentScore == null) {
                currentScore = 0;
            }
            scorePerCategory.put(category, currentScore + 1);
        } else {
            scorePerCategory.put(category, 1);
        }
        Log.d(TAG, "Update score per category: " + category + " in Map: " + getQuizResult().getScorePerCategory());
    }


    @Override
    public void updateScorePerFrequency(boolean isCorrect, QuestionDto question) {
        Map<String, Integer> scorePerFrequency = getQuizResult().getScorePerFrequency();
        String correctAnswer = question.getCorrectOption();
        if (isCorrect) {
            if (scorePerFrequency.containsKey(correctAnswer)) {
                Integer currentScore = scorePerFrequency.get(correctAnswer);
                if (currentScore == null) {
                    currentScore = 0;
                }
                scorePerFrequency.put(correctAnswer, currentScore + 1);
            } else {
                scorePerFrequency.put(correctAnswer, 1); // Initialize score for new frequency
            }
        }
        Log.d(TAG, "Update score per frequency: " + correctAnswer + " in Map: " + getQuizResult().getScorePerFrequency());
    }

    private UserStatistics userStatistics;
    private DataCache dataCache;
    private DataUtils dataUtils;
    private int currentLives;
    private long startTime;
    private int score = 0;
    private int questionCount;
    private Integer maxQuestions;
    private final Map<Integer, String> intervalButtonsIndex = new HashMap<>(MusicData.INTERVAL_BUTTONS);
    private final MutableLiveData<String> quizCategory = new MutableLiveData<>();
    private final MutableLiveData<Integer> quizChapter = new MutableLiveData<>();

    public boolean submitAnswer(QuestionDto question, int selectedOptionIndex, int selectedOptionIndex2) {
        boolean isCorrect;
        if (selectedOptionIndex == -1 || question == null) return false;
        String category = question.getCategory();
        if ("intervals".equals(category) && selectedOptionIndex2 != -1) {
            isCorrect = submitIntervalAnswer(selectedOptionIndex, selectedOptionIndex2, question);
        } else if ("pitch".equals(category)) {
            isCorrect = submitPitchAnswer(selectedOptionIndex, question);
        } else {
            isCorrect = submitTextAnswer(question.getId(), selectedOptionIndex);
        }
        return isCorrect;
    }

    private boolean submitTextAnswer(int questionId, int selectedOptionIndex) {
        Log.d(TAG, "submitTextAnswer - questionId: " + questionId + " and selectedOption(s): " + selectedOptionIndex);
        boolean isCorrect = selectedOptionIndex == getCorrectAnswerFromDatabase(questionId);
        Log.d(TAG, "Submitting text answer");
        return isCorrect;
    }
    private boolean submitPitchAnswer(int selectedOptionIndex, QuestionDto question) {
        Log.d(TAG, "submitPitchAnswer: - correctAnswer = " + question.getAnswerNr() + " and selectedOption: " + selectedOptionIndex);
        boolean isCorrect = selectedOptionIndex == question.getAnswerNr();
        Log.d(TAG, "Submitting text answer");
        return isCorrect;
    }
    private boolean submitIntervalAnswer(int selectedOptionIndex1, int selectedOptionIndex2,
                                         QuestionDto question) {
        String selectedIntervalQuality = intervalButtonsIndex.get(selectedOptionIndex1);
        String selectedIntervalNumber = intervalButtonsIndex.get(selectedOptionIndex2);
        String correctIntervalQuality = question.getOption1();
        String correctIntervalNumber = question.getOption2();
        Pair<String, String> selectedAnswerPair = new Pair<>(selectedIntervalNumber, selectedIntervalQuality);
        Pair<String, String> correctAnswerPair = new Pair<>(correctIntervalQuality, correctIntervalNumber);
        Log.d(TAG, "submitAnswer: selectedAnswerPair = " + selectedAnswerPair);
        return Objects.equals(selectedAnswerPair, correctAnswerPair);
    }



    // START_QUIZ_DATA_MANAGEMENT
    // --------------------------------------------------------------------------------------------
    public int startQuiz(String category, int chapter, String quizType) {
        maxQuestions = 10;
        if (chapter == 4) {
            maxQuestions = 15;
        }
        if (category.equals("intervals")) {
           // String selectedRootNote = quizType;
          //  setRootNote(selectedRootNote);
          //  quizType = "play";
        }
        startTime = System.currentTimeMillis(); // Start the quiz timer
        quizCategory.setValue(category);
        quizChapter.setValue(chapter);
        addQuizResultEntry(category, chapter, quizType);  // Add a new quiz result entry

        Log.d(TAG, "startQuiz: category = " + category + ", chapter = " + chapter + ", quizType = " + quizType);

        return maxQuestions;
    }


    // QUIZ_RESULTS_TABLE_MANAGEMENT
    // --------------------------------------------------------------------------------------------
    // Clear all quiz results from the cache
    private void clearQuizResults() {
        SQLiteDatabase dbLocal = dbHelper.getWritableDatabase();
        dbLocal.delete(QuestionContract.QuizResultEntry.TABLE_NAME, null, null);
        Log.d(TAG, "Cleared results from cache");
    }
    // Add a new quiz result entry to the cache
    @Override
    public void addQuizResultEntry(String category, int chapter, String quizType) {
        clearQuizResults(); // Clear the quiz results table
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_TYPE, quizType);
        long quizDate = System.currentTimeMillis();
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_DATE, quizDate);
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_CATEGORY, category);
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_CHAPTER, chapter);
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_SCORE, 0); // Initial score is 0
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_DURATION, 0); // Initial duration is 0
        db.insertGeneralStatsLocal(QuestionContract.QuizResultEntry.TABLE_NAME, null, values);
    }

    // Update the latest quiz result entry in the cache
    private void updateQuizResultEntry(long quizDate, int score, int duration) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_DATE, quizDate);
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_SCORE, score);
        values.put(QuestionContract.QuizResultEntry.COLUMN_NAME_DURATION, duration);

        // Fetch the latest entry
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionContract.QuizResultEntry.TABLE_NAME + " ORDER BY " + BaseColumns._ID + " DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            // Get column indices safely
            int categoryIndex = cursor.getColumnIndex(QuestionContract.QuizResultEntry.COLUMN_NAME_CATEGORY);
            int chapterIndex = cursor.getColumnIndex(QuestionContract.QuizResultEntry.COLUMN_NAME_CHAPTER);

            // Ensure the columns exist
            if (categoryIndex != -1 && chapterIndex != -1) {
                String category = cursor.getString(categoryIndex);
                int chapter = cursor.getInt(chapterIndex);

                // Update the latest entry
                String selection = QuestionContract.QuizResultEntry.COLUMN_NAME_CATEGORY + " = ? AND " + QuestionContract.QuizResultEntry.COLUMN_NAME_CHAPTER + " = ?";
                String[] selectionArgs = { category, String.valueOf(chapter) };
                db.update(
                        QuestionContract.QuizResultEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
            } else {
                // Handle the case where columns are not found
                Log.e("YourClassName", "Column indices not found");
            }
        }
        cursor.close();
    }
    // Fetch the latest quiz result entry from the cache
  @Override
    public QuizResult getQuizResult() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                QuestionContract.QuizResultEntry.COLUMN_NAME_CATEGORY,
                QuestionContract.QuizResultEntry.COLUMN_NAME_CHAPTER,
                QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_DATE,
                QuestionContract.QuizResultEntry.COLUMN_NAME_SCORE,
                QuestionContract.QuizResultEntry.COLUMN_NAME_DURATION,
                QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_TYPE
        };
        Cursor cursor = db.query(QuestionContract.QuizResultEntry.TABLE_NAME, projection, null, null, null, null, null);


       QuizResult quizResult = null;
       if (cursor.moveToFirst()) {
           Timestamp quizDate = new Timestamp(cursor.getLong(cursor.getColumnIndexOrThrow(QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_DATE)), 0);
           String category = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuizResultEntry.COLUMN_NAME_CATEGORY));
           int chapter = cursor.getInt(cursor.getColumnIndexOrThrow(QuestionContract.QuizResultEntry.COLUMN_NAME_CHAPTER));
           String quizType = cursor.getString(cursor.getColumnIndexOrThrow(QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_TYPE));
           int quizScore = cursor.getInt(cursor.getColumnIndexOrThrow(QuestionContract.QuizResultEntry.COLUMN_NAME_SCORE));
           int duration = cursor.getInt(cursor.getColumnIndexOrThrow(QuestionContract.QuizResultEntry.COLUMN_NAME_DURATION));
           Map<String, Integer> scorePerCategory = cursor.getColumnNames().length > 5 ? new HashMap<>() : null;
           Map<String, Integer> scorePerFrequency = cursor.getColumnNames().length > 6 ? new HashMap<>() : null;
        //   quizResult = new QuizResult(quizTimeData, quizDate, maxQuestions, questionCounter, quizScore, lives, rootNote, duration, category, chapter, quizType, scorePerCategory, scorePerFrequency, startTime, endTime);
       }
       cursor.close();
       return quizResult;
   }

    // END_QUIZ_DATA_MANAGEMENT
    // --------------------------------------------------------------------------------------------
    @Override
    public QuizResult endQuiz() {
        Log.d(TAG, "endQuiz called");
        long quizDate = System.currentTimeMillis();
        // Calculate and set the quiz Duration
        long endTime = System.currentTimeMillis();
        int duration = (int) ((endTime - startTime) / 1000);  // Duration in seconds
        score = userScore != -1 ? userScore : 0;   // Get the final score
        // Update the quiz result entry in the SQLite cache
        updateQuizResultEntry(quizDate, score, duration);
        // Process the quiz results
        return processQuizResult();
    }

    private QuizResult processQuizResult() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        QuizResult quizResult = new QuizResult();

        // Fetch the latest entry
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionContract.QuizResultEntry.TABLE_NAME + " ORDER BY " + BaseColumns._ID + " DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            // Get column indices safely
            int quizTypeIndex = cursor.getColumnIndex(QuestionContract.QuizResultEntry.COLUMN_NAME_QUIZ_TYPE);
            int categoryIndex = cursor.getColumnIndex(QuestionContract.QuizResultEntry.COLUMN_NAME_CATEGORY);
            int chapterIndex = cursor.getColumnIndex(QuestionContract.QuizResultEntry.COLUMN_NAME_CHAPTER);
            int scoreIndex = cursor.getColumnIndex(QuestionContract.QuizResultEntry.COLUMN_NAME_SCORE);
            int durationIndex = cursor.getColumnIndex(QuestionContract.QuizResultEntry.COLUMN_NAME_DURATION);

            // Ensure the columns exist
            if (quizTypeIndex != -1 && categoryIndex != -1 && chapterIndex != -1 && scoreIndex != -1 && durationIndex != -1) {
                String quizType = cursor.getString(quizTypeIndex);
                String category = cursor.getString(categoryIndex);
                int chapter = cursor.getInt(chapterIndex);
                int score = cursor.getInt(scoreIndex);
                int duration = cursor.getInt(durationIndex);
                Timestamp quizDate = new Timestamp(System.currentTimeMillis() / 1000, 0);

                Log.d(TAG, "_________________________________________________________________________________________");
                Log.d(TAG,   "SQLite DB entry ---> Score: " + score + ", Duration: " + duration + "Category: " + category + ", Chapter: " + chapter + ", Quiz Type: " + quizType);
                Log.d(TAG, "_________________________________________________________________________________________");

                quizResult.setQuizDate(quizDate);
                quizResult.setQuizType(quizType);
                quizResult.setCategory(category);
                quizResult.setChapter(chapter);
                quizResult.setScore(score);
                quizResult.setDuration(duration);
            } else {
                // Handle the case where columns are not found
                Log.e(TAG, "Column indices not found");
            }
        }
        cursor.close();
        userStatisticsRepository.updateUserStatisticsFromQuizResults(quizResult);
        return quizResult;
    }

        public void decrementUserLives() {
        userStatistics = dataCache.getCachedObject();
        currentLives = getCurrentLivesLiveData();

        if  (currentLives > 0) {
            currentLives -= 1;
            userStatistics.getGeneralStats().setNumberOfLives(currentLives -1);
            dataCache.cacheData(userStatistics);
            Log.d(TAG, "Lives decremented. Current number of lives: " + userStatistics.getGeneralStats().getNumberOfLives());
        } else {
            Log.d("QuestionViewModel", "User has no more lives.");
        }
    }

       @Override
    public Integer incrementQuestionCount() {
        questionCount += 1;

        // Check if it's the 10th question
        if (maxQuestions != null && questionCount + 1 >= maxQuestions) {
           // endQuiz();
        }
        return questionCount;
    }*/

}
