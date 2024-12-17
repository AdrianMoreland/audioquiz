package com.audioquiz.core.domain.usecase.quiz.usecase_impl.quiz;

import com.audioquiz.core.domain.repository.quiz.QuizRepository;
import com.audioquiz.core.model.quiz.QuizResult;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;


public class GetQuizResultDataImpl {
    private final QuizRepository quizRepository;
    private final Single<QuizResult> quizResult;

    @Inject
    public GetQuizResultDataImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
        this.quizResult = getQuizResultSingle();
    }


    public int getMaxQuestions() {
        return quizRepository.getMaxQuestions();
    }

    public String getCategory() {
        return quizRepository.getCategory();
    }

    public int getChapter() {
        return quizRepository.getChapter();
    }

    public String getQuizType() {
        return quizRepository.getQuizType();
    }

    public String getRootNote() {
        return quizRepository.getRootNote();
    }

    public Single<QuizResult> getQuizResultSingle() {
        return quizRepository.getQuizResultSingle();
    }

    public Single<String> getCurrentLivesSingle() {
         return getQuizResultSingle()
                .map(quizResult -> quizRepository.getCurrentLives());
    }

    public Single<String> getUserScoreSingle() {
         return getQuizResultSingle()
                .map(quizResult -> String.valueOf(quizRepository.getUserScore()));
    }

    public Single<Integer> getMaxQuestionsSingle() {
        return getQuizResultSingle()
                .map(QuizResult::getMaxQuestions);
    }

    public Observable<Boolean> getIsCorrectSingle() {
        return quizRepository.getIsCorrectObservable();
    }

    public Observable<Boolean> getIsQuizEndedObservable() {
        return quizRepository.getIsQuizEndedObservable(); // Assuming repository provides an Observable
    }

    public Observable<Boolean> getIsLastQuestionObservable() {
        return quizRepository.getIsLastQuestionObservable(); // Assuming repository provides an Observable
    }

    public Single<String> getQuizMedalSingle() {
        return getQuizResultSingle()
                .map(quizResult -> {
                    int maxQuestions = quizResult.getMaxQuestions();
                    int score = quizResult.getScore();
                    float percentage = (float) score / maxQuestions * 100;

                    String medal;
                    switch ((int) percentage) {
                        case 100:
                            medal = "gold";
                            break;
                        case 90: case 91: case 92: case 93: case 94: case 95: case 96: case 97: case 98: case 99:
                            medal = "silver";
                            break;
                        case 80: case 81: case 82: case 83: case 84: case 85: case 86: case 87: case 88: case 89:
                            medal = "bronze";
                            break;
                        default:
                            medal = "failed";
                    }
                    return medal;
                });
    }


    public Observable<Boolean> getIsCorrectObservable() {
        return Observable.create(emitter -> {
            emitter.onNext(quizRepository.getIsCorrect());
            // Consider adding onErrorComplete for cleanup if needed
        });
    }

    public void resetIsQuizEnded() {
        quizRepository.setIsQuizOver(false);
    }

    public Integer getCorrectOption2() {
        return quizRepository.getCorrectOption2();
    }

    public Integer getCorrectOption1() {
        return quizRepository.getCorrectOption1();
    }


/*
    public LiveData<Integer> getMaxQuestionsLiveData() {
        MutableLiveData<Integer> maxQuestionsLiveData = new MutableLiveData<>();
        maxQuestionsLiveData.postValue(quizRepository.getMaxQuestions());
        return maxQuestionsLiveData;
    }

    public LiveData<String> getCurrentLivesLiveData() {
        return Transformations.map(quizResult, quizResult -> {
            if (quizResult != null) {
                return DataUtils.getStringValue(quizResult.getLives());
            } else {
                return "N/A";
            }
        });
    }

    public LiveData<String> getUserScoreLiveData() {
        return Transformations.map(quizResult, quizResult -> {
            if (quizResult != null) {
                return DataUtils.getStringValue(quizResult.getLives());
            } else {
                return "N/A";
            }
        });
    }

    public LiveData<Boolean> getIsCorrectLiveData() {
        return quizRepository.getIsCorrect();
    }

    public LiveData<Boolean> getIsQuizEndedLiveData() {
        return quizRepository.getIsQuizEndedLiveData();
    }

    public LiveData<Boolean> getIsLastQuestion() {
        return quizRepository.getIsLastQuestion();
    }

    public LiveData<String> getQuizMedalLiveData() {
        return Transformations.map(quizResult, quizResult -> {
            if (quizResult != null) {
                String medal = "N/A";
                int maxQuestions = quizResult.getMaxQuestions();
                int score = quizResult.getScore();
                float percentage = (float) score / maxQuestions * 100;

                if (percentage == 100) {
                    medal = "gold";
                } else if (percentage >= 90) {
                    medal = "silver";
                } else if (percentage >= 80) {
                    medal = "bronze";
                } else {
                    medal = "failed";
                }
                return medal;
            } else {
                return "N/A";
            }
        });
    }
*/

}
