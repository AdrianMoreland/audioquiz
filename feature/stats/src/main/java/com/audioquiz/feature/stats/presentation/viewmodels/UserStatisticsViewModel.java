package com.audioquiz.feature.stats.presentation.viewmodels;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.audioquiz.core.domain.usecase.user.profile.UserProfileUseCaseFacade;
import com.audioquiz.core.domain.usecase.user.stats.StatisticsUseCaseFacade;
import com.audioquiz.core.model.user.UserProfile;
import com.audioquiz.core.model.user.stats.CategoryStatsData;
import com.audioquiz.core.model.user.stats.FrequencyStats;
import com.audioquiz.core.model.user.stats.GeneralStats;
import com.audioquiz.core.model.user.stats.Last7DaysScores;
import com.audioquiz.core.model.user.stats.UserStats;
import com.audioquiz.designsystem.util.ImageSelectionUtil;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class UserStatisticsViewModel extends ViewModel {
    private static final String TAG = "UserStatisticsViewModel";
    private final StatisticsUseCaseFacade userStatsUseCaseFacade;
    private final UserProfileUseCaseFacade userProfileUseCaseFacade;
    private final ImageSelectionUtil imageSelectionUtil;
    private final MutableLiveData<UserStats> userStatsLiveData = new MutableLiveData<>();
    private final MutableLiveData<UserProfile> userProfileData;
    private final MutableLiveData<GeneralStats> generalStatsLiveData = new MutableLiveData<>();
    private final MutableLiveData<CategoryStatsData> categoryStatsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> usernameLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> userLetterLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> profileImageUrlLiveData = new MutableLiveData<>();
    /*    private final MutableLiveData<Integer> totalScoreLiveData = new MutableLiveData<>();
        private final MutableLiveData<Double> averageScoreLiveData = new MutableLiveData<>();
        private final MutableLiveData<Integer> numberOfQuizzesLiveData = new MutableLiveData<>();
        private final MutableLiveData<Integer> longestStreakLiveData = new MutableLiveData<>();
        private final MutableLiveData<Integer> currentStreakLiveData = new MutableLiveData<>();
        private final MutableLiveData<Integer> userLevelLiveData = new MutableLiveData<>();*/
    private final MutableLiveData<Last7DaysScores> last7DaysScoresMapLiveData = new MutableLiveData<>();
    private final MutableLiveData<FrequencyStats> frequencyStatsLiveData = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    //    private final MutableLiveData<Map<String, Map<String, Object>>>  frequencyStatsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Entry>> entriesLiveData = new MutableLiveData<>();
    @Inject
    public UserStatisticsViewModel(StatisticsUseCaseFacade userStatsUseCaseFacade,
                                   UserProfileUseCaseFacade userProfileUseCaseFacade,
                                   ImageSelectionUtil imageSelectionUtil) {
        this.userStatsUseCaseFacade = userStatsUseCaseFacade;
        this.userProfileUseCaseFacade = userProfileUseCaseFacade;
        this.imageSelectionUtil = imageSelectionUtil;
        this.userProfileData = new MutableLiveData<>();

        fetchUserStats();
        fetchUserProfile();
    }

    //region DISPOSABLES_LIVE_DATA
    private void fetchUserStats() {
        Disposable disposable = userStatsUseCaseFacade.getUserStatsSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userStats -> {
                    Timber.tag(TAG).d("UserStats fetch single: %s", userStats);
                    userStatsLiveData.postValue(userStats);
                    generalStatsLiveData.postValue(userStats.getGeneralStats());
                    last7DaysScoresMapLiveData.postValue(userStats.getLast7DaysScores());
                    frequencyStatsLiveData.postValue(userStats.getFrequencyStats());
                }, throwable -> {
                    // Handle error
                });
        compositeDisposable.add(disposable);
    }


    private void fetchUserProfile() {
        Disposable disposable = userProfileUseCaseFacade.getUserProfileSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> {
                    userProfileData.postValue(userProfile);
                    usernameLiveData.postValue(userProfile.getUsername());
                    userLetterLiveData.postValue(userProfile.getUsername().substring(0, 1).toUpperCase());
                    profileImageUrlLiveData.postValue(userProfile.getProfileImage());
                }, throwable -> {
                    // Handle error, e.g., show a Toast or log the error
                    Timber.tag("UserProfile").e(throwable, "Error fetching user profile");
                });
        compositeDisposable.add(disposable);
    }

    // User Profile LiveData Getters
    public LiveData<String> getUsernameLiveData() {
        return usernameLiveData;
    }

    public LiveData<String> getUsernameLetterLiveData() {
        Timber.tag(TAG).d("getUserLetterLiveData: %s", userLetterLiveData.getValue());
        return userLetterLiveData;
    }

    public LiveData<String> getProfileImageUrlLiveData() {
        return profileImageUrlLiveData;
    }


    public LiveData<GeneralStats> getGeneralStats() {
        return Transformations.map(userStatsLiveData, UserStats::getGeneralStats);
    }

    public LiveData<Integer> getUserLevel() {
        return Transformations.map(getGeneralStats(), GeneralStats::getUserLevel);
    }

    public LiveData<Integer> getTotalScore() {
        return Transformations.map(getGeneralStats(), GeneralStats::getTotalScore);
    }

    public LiveData<List<Entry>> getFrequencyStats() {
        return entriesLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
    //endregion

    //region LIVE_DATA_SETTERS_AND_GETTERS
    // LIVE_DATA_GETTERS
    // User Statistics Live Data Getters
    public LiveData<Integer> getUserLevelLiveData() {
        return Transformations.map(getGeneralStats(), GeneralStats::getUserLevel);
    }

    public LiveData<Integer> getTotalScoreLiveData() {
        return Transformations.map(getGeneralStats(), GeneralStats::getTotalScore);
    }

    public LiveData<Double> getAverageScoreLiveData() {
        return Transformations.map(getGeneralStats(), GeneralStats::getAverageScore);
    }

    public LiveData<Integer> getNumberOfQuizzesLiveData() {
        return Transformations.map(getGeneralStats(), GeneralStats::getNumberOfQuizzes);
    }

    public LiveData<Integer> getLongestStreakLiveData() {
        return Transformations.map(getGeneralStats(), GeneralStats::getLongestStreak);
    }

    public LiveData<Integer> getCurrentStreakLiveData() {
        return Transformations.map(getGeneralStats(), GeneralStats::getCurrentStreak);
    }

    //endregion

    //region IMAGE_SELECTION_AND_UPLOAD

    // In UserStatisticsViewModel
    public void selectImage(
            Fragment fragment,
            Uri uri, // Changed to Uri
            ActivityResultLauncher<Intent> cropLauncher
    ) {
        imageSelectionUtil.selectAndCropImage(fragment, uri, cropLauncher, resultUri -> {
            if (resultUri != null) {
                profileImageUrlLiveData.setValue(resultUri.toString());
                // Or upload the image and update with the uploaded URL
            }
        });
    }
    //endregion

    //region FREQUENCY_STATS
    public List<Entry> execute() {
        List<Entry> liveData = new ArrayList<>();
        if (frequencyStatsLiveData == null) {
            System.out.println(TAG + "FrequencyStatsLiveData is null");
//            frequencyStatsLiveData = userStatisticsRepository.getFrequencyStats();
            getFrequencyStats();
        }

        return liveData;
    }

    private void processFrequencyStats(FrequencyStats value, List<Entry> liveData) {
        Map<String, Map<String, Object>> frequencyStatsMap = getFrequencyStatsMap(value);
        List<Double> accuracyRates = new ArrayList<>();
        Map<String, Double> accuracyRatesMap = new HashMap<>();

        if (frequencyStatsMap != null) {
            addValuesToAccuracyData(frequencyStatsMap, accuracyRates, accuracyRatesMap);
        }

        Map<String, Double> standardizedScores = getStandardizedScores(accuracyRates, accuracyRatesMap);

        List<Entry> entries = getEntriesList(standardizedScores);

        System.out.println(TAG + "Entries: " + entries);
    }

    private void addValuesToAccuracyData(Map<String, Map<String, Object>> frequencyStatsMap, List<Double> accuracyRates, Map<String, Double> accuracyRatesMap) {
        for (Map.Entry<String, Map<String, Object>> entry : frequencyStatsMap.entrySet()) {
            String key = entry.getKey();
            Map<String, Object> value = entry.getValue();

            Integer score = value.get("score") != null ?  (Integer) value.get("score") : null;
            Integer questionCount = value.get("totalQuestions") != null ?  (Integer) value.get("totalQuestions") : null;
            if (score != null && questionCount != null) {
            // Calculate accuracy rate
            double accuracyRate = ((double) score / questionCount) * 100;
            System.out.println(TAG + "Accuracy rate: " + accuracyRate);
            // Add to chart data
            accuracyRates.add(accuracyRate);
            // Add frequency key and accuracy rate to map
            accuracyRatesMap.put(key, accuracyRate);
            }
        }
    }

    private Map<String, Double> getStandardizedScores(List<Double> accuracyRates, Map<String, Double> accuracyRatesMap) {
        Map<String, Double> standardizedScores = new HashMap<>();

        double averageAccuracy = accuracyRates.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double variance = accuracyRates.stream().mapToDouble(rate -> Math.pow(rate - averageAccuracy, 2)).average().orElse(0);
        double standardDeviation = Math.sqrt(variance);

        for (Map.Entry<String, Double> entry : accuracyRatesMap.entrySet()) {
            String frequency = entry.getKey();
            double accuracyRate = entry.getValue();
            double standardizedScore = (accuracyRate - averageAccuracy) / standardDeviation;
            standardizedScores.put(frequency, standardizedScore);
        }
        return standardizedScores;
    }

    private Map<String, Map<String, Object>> getFrequencyStatsMap(FrequencyStats frequencyStats) {
        if (frequencyStats != null) {
            return null;
        } else {
            Timber.tag(TAG).d("");
            return new HashMap<>();
        }
    }

    private List<Entry> getEntriesList(Map<String, Double> standardizedScores) {
        List<Entry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : standardizedScores.entrySet()) {
            String frequency = entry.getKey();
            double score = entry.getValue();
            entries.add(new Entry(Float.parseFloat(frequency.replace("Hz", "")), (float) score));
        }
        return entries;
    }

    //endregion




    //region TRASH
    /*    // In UserStatisticsViewModel
    public void handleCropResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            final Uri resultUri = UCrop.getOutput(result.getData());
            if (resultUri != null) {
                profileImageUrlLiveData.setValue(resultUri.toString());
                // Or upload the image and update with the uploaded URL
            }
        } else if (result.getResultCode() == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(result.getData());// Handle the crop error
        }
    }

    // In UserStatisticsViewModel
    public void handleImageSelectionResult(ActivityResult result, ActivityResultLauncher<Intent> uCropActivityResultLauncher) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                startCrop(selectedImageUri, uCropActivityResultLauncher);
            }
        }
    }*/
        /*
    private void fetchFrequencyStats() {
        statisticsUseCaseFacade.getFrequencyStats().observeForever(this::setFrequencyStats);
    }
    public LiveData<Map<String, Map<String, Object>>> getFrequencyStats() {
        return frequencyStatsLiveData;
    }

    public void setFrequencyStats(Map<String, Map<String, Object>> data) {
        Log.d(TAG, "Setting frequency stats: " + data);
        frequencyStatsLiveData.setValue(data);
    }
       public void handleCropResult(ActivityResult result) {
        userProfileUseCaseFacade.handleCropResult(result, new () {
            @Override
            public void onImageUploadSuccess(String downloadUrl) {
                profileImageUrlLiveData.postValue(downloadUrl);
            }

            @Override
            public void onImageUploadFailure(Exception e) {
                // Handle the error
            }
        });
    }

       public void uploadProfileImage(Uri imageUri, OnImageUploadListener listener) {
        java.net.URI javaUri;
        try {
            javaUri = new java.net.URI(imageUri.toString());
        } catch (URISyntaxException e) {
            // Handle the error
            return;
        }
        userProfileUseCaseFacade.uploadProfileImage(javaUri, new UploadProfileImageUseCase.OnImageUploadListener() {
            @Override
            public void onImageUploadSuccess(String downloadUrl) {
                profileImageUrlLiveData.setValue(downloadUrl);
                listener.onImageUploadSuccess(downloadUrl);
            }

            @Override
            public void onImageUploadFailure(Exception e) {
                listener.onImageUploadFailure(e);
            }
        });
    }

    public void setProfileImageUrlLiveData(String downloadUrl) {
        profileImageUrlLiveData.setValue(downloadUrl);
    }
    public void selectImage(ActivityResultLauncher<Intent> launcher) {
        userProfileUseCaseFacade.selectImage(launcher);
    }

        public void handleImageSelectionResult(ActivityResult result, ActivityResultLauncher<Intent> uCropActivityResultLauncher) {
        userProfileUseCaseFacade.handleImageSelectionResult(result, uri -> startCrop(uri, uCropActivityResultLauncher));
    }*/
    //endregion
}