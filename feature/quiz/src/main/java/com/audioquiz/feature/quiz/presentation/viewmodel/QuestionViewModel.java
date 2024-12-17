package com.audioquiz.feature.quiz.presentation.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audioquiz.core.domain.usecase.quiz.question.FrequenciesDataUseCase;
import com.audioquiz.core.domain.usecase.quiz.question.QuestionUseCaseFacade;
import com.audioquiz.core.domain.usecase.quiz.usecase.QuizUseCaseFacade;
import com.audioquiz.core.model.quiz.Question;
import com.audioquiz.designsystem.util.UiMapper;
import com.audioquiz.feature.quiz.model.QuestionUi;
import com.audioquiz.feature.quiz.model.QuizResultUi;
import com.audioquiz.library.util.StringExtensions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class QuestionViewModel extends ViewModel {
    // Constants
    private static final String TAG = "QuestionViewModel";
    // Database Helper
    private final QuestionUseCaseFacade questionUseCaseFacade;
    private final QuizUseCaseFacade quizUseCaseFacade;
    private final FrequenciesDataUseCase frequenciesDataUseCase;
//    private final UiMapper <Question, QuestionUi> questionMapper = new UiMapper<>();
//    private final UiMapper <QuizResult, QuizResultUi> quizResultMapper = new UiMapper<>();

    // LiveData Variables
    private final MutableLiveData<QuestionUi> questionLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> questionCountLiveData = new MutableLiveData<>();
    public final MutableLiveData<Integer> userScoreLiveData = new MutableLiveData<>(0); // Initialize to 0
    private final MediatorLiveData<Integer> correctOption1 = new MediatorLiveData<>();
    private final MediatorLiveData<Integer> correctOption2 = new MediatorLiveData<>();
    private final MutableLiveData<String> quizMedalLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isCorrectLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<String> userScore = new MutableLiveData<>();
    private final MutableLiveData<String> numberOfLives = new MutableLiveData<>();
    private final MutableLiveData<Integer> maxQuestionsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isQuizEnded = new MutableLiveData<>(false);
    private Disposable disposable;

    // Constructor
    @Inject
    public QuestionViewModel(QuestionUseCaseFacade questionUseCaseFacade,
                             QuizUseCaseFacade quizUseCaseFacade,
                             FrequenciesDataUseCase frequenciesDataUseCase) {
        this.questionUseCaseFacade = questionUseCaseFacade;
        this.quizUseCaseFacade = quizUseCaseFacade;
        this.frequenciesDataUseCase = frequenciesDataUseCase;
        loadQuizData(); // Call this to fetch data initially

        correctOption1.addSource(new MutableLiveData<>(quizUseCaseFacade.getCorrectOption1()), correctOption1::setValue);
        correctOption2.addSource(new MutableLiveData<>(quizUseCaseFacade.getCorrectOption2()), correctOption2::setValue);
    }

    // LiveData Getters
    public LiveData<QuestionUi> getQuestionLiveData() {
        return questionLiveData;
    }
    public LiveData<Integer> getQuestionCountLiveData() {
        return questionCountLiveData;
    }
    public LiveData<Integer> getCorrectOption1() {
        return correctOption1;
    }
    public LiveData<Integer> getCorrectOption2() {
        return correctOption2;
    }
    public LiveData<Boolean> getIsCorrectLiveData() {
        return isCorrectLiveData;
    }
    public LiveData<String> getUserScore() {
        return userScore;
    }
    public LiveData<String> getNumberOfLives() {
        return numberOfLives;
    }
    public LiveData<Integer> getMaxQuestionsLiveData() {
        return maxQuestionsLiveData;
    }
    public LiveData<Boolean> getIsQuizEnded() {
        return isQuizEnded;
    }
    public LiveData<String> getQuizMedal() {
        return quizMedalLiveData;
    }
    private QuizResultUi previousQuizResult; // Store previous QuizResult for comparison

    public void loadQuizData() {
        AtomicBoolean hasUpdated = new AtomicBoolean(false); // Declare without initialization
        disposable = quizUseCaseFacade.getQuizResultSingle()
                .subscribeOn(Schedulers.io())
                .map(quizResult -> UiMapper.mapToUi(quizResult, QuizResultUi.class))
                .subscribe(quizResult -> {
                    hasUpdated.set(true);
                    previousQuizResult = quizResult; // Update for next comparison
                    // Update LiveData objects based on QuizResult values
                    userScoreLiveData.setValue((quizResult).getScore());
                    userScore.setValue(StringExtensions.getStringValue(quizResult.getScore())); // User-friendly display
                    numberOfLives.setValue(StringExtensions.getStringValue((quizResult).getLives()));
                    maxQuestionsLiveData.setValue((quizResult).getMaxQuestions());

                }, throwable -> {
                    // Handle errors
                });
    }



    // START_QUIZ
    public void startQuiz(String category, int chapter, String quizType) {
        questionUseCaseFacade.setQuizSelection(category, chapter, quizType);
        quizUseCaseFacade.startQuiz(category, chapter, quizType);
    }

    // LOAD_QUESTION_TO_FRAGMENT
    public void loadRandomQuestionToFragment2() {
        Question question = questionUseCaseFacade.getQuestion().blockingGet();
        QuestionUi questionUi = UiMapper.mapToUi(question, QuestionUi.class);
        questionLiveData.setValue(questionUi);
    }

    public void loadNextQuestion() {
        disposable = questionUseCaseFacade.getQuestion()
                // Chain RxJava operators here
                .subscribeOn(Schedulers.io()) // Perform network/database operations on background thread
                .observeOn(Schedulers.computation()) // Optional: Perform transformations on computation thread
                .map(question -> UiMapper.mapToUi(question, QuestionUi.class))
                .subscribe(questionLiveData::setValue, throwable -> {
                    // Handle errors
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose(); // Unsubscribe to prevent memory leaks
    }

/*    private LiveData<QuestionUi> getQuestionUiLiveData() {
        MutableLiveData<QuestionUi> questionLiveData = new MutableLiveData<>();
        QuestionUi questionUi = questionUiMapper.toEntity(questionUseCaseFacade.getQuestion());
        questionLiveData.setValue(questionUi);
        return questionLiveData;
    }

    private void loadQuestion() {
        Question question = questionUseCaseFacade.getQuestion();
        QuestionUi questionUi = questionUiMapper.toEntity(question);
        questionLiveData.setValue(questionUi);
    }*/

    public List<Integer> disableIncompatibleButtons(int index) {
        Map<Integer, String> intervalButtonsMap = frequenciesDataUseCase.getIntervalButtons();
        Map<String, List<String>> intervalQualities = frequenciesDataUseCase.getIntervalQualities();
        List<Integer> incompatibleButtonsIndex = new ArrayList<>();

        if (index <= 7 && index != -1) {
            String indexText = intervalButtonsMap.get(index);
            List<String> compatibleQualities = intervalQualities.get(indexText);

            if (compatibleQualities != null) {
                for (Map.Entry<Integer, String> entry : intervalButtonsMap.entrySet()) {
                    if (entry.getKey() >= 8 && !compatibleQualities.contains(entry.getValue())) {
                        incompatibleButtonsIndex.add(entry.getKey());
                    }
                }
            }
            return incompatibleButtonsIndex;
        }
        return incompatibleButtonsIndex;
    }

    // ANSWER_SUBMISSION_AND_CHECKING
    public void submitUserAnswer(int selectedOptionIndex, int selectedOptionIndex2) {
        if (selectedOptionIndex == -1 || questionLiveData.getValue() == null) return;
        quizUseCaseFacade.submitAnswer(selectedOptionIndex, selectedOptionIndex2);
        loadNextQuestion(); // Load the next question after handling answer
    }


    // NAVIGATE_TO_QUIZ_RESULT
    public void resetIsQuizEnded() {
        quizUseCaseFacade.resetIsQuizEnded();
    }

    public void resetUserScore() {
        userScoreLiveData.setValue(0);
    }

    public void resetQuestionCounter() {
        questionCountLiveData.setValue(0);
    }

    // END_QUIZ_IF_MAX_QUESTIONS_REACHED
    public void endQuiz() {
        Log.d(TAG, "endQuiz called");
        quizUseCaseFacade.endQuiz();
    }

    public void playPitchFrequency(int frequency) {
        questionUseCaseFacade.playPitchFrequency(frequency);
    }

    public void playIntervalFrequency(double rootNote, double secondNote) {
        questionUseCaseFacade.playIntervalFrequency(rootNote, secondNote);
    }

    public void playFrequency(QuestionUi questionUi) {
        Question question = UiMapper.mapToDomain(questionUi, Question.class);
        questionUseCaseFacade.playFrequency(question);
    }

    public LiveData<Boolean> getIsLastQuestion() {
        MutableLiveData<Boolean> isLastQuestion = new MutableLiveData<>();
        isLastQuestion.postValue(quizUseCaseFacade.getIsLastQuestion());
        return isLastQuestion;
    }




/*
    private QuizResultUi mapToUi(QuizResult quizResult) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(quizResult, QuizResultUi.class);
    }*/
    /*
    private void decrementLives() {
        quizUseCaseFacade.decrementLives();
    }

       public void incrementQuestionCount() {
        Log.d(TAG, "incrementQuestionCount called");
        Pair<Integer, Boolean> result = quizUseCaseFacade.incrementQuestionCount();
        questionCountLiveData.setValue(result.first);
        isQuizOverLiveData.setValue(result.second);
        if (Boolean.TRUE.equals(result.second)) {
            endQuiz();
        }
    }

     public void incrementUserScore() {
        quizUseCaseFacade.updateUserScore();
        userScoreLiveData.setValue(quizUseCaseFacade.getUserScoreLiveData());
    }*/
}