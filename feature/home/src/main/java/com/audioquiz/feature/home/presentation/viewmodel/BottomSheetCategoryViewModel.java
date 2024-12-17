package com.audioquiz.feature.home.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audioquiz.core.domain.usecase.user.stats.StatisticsUseCaseFacade;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class BottomSheetCategoryViewModel extends ViewModel {

    private final StatisticsUseCaseFacade statisticsUseCaseFacade;
    private final MutableLiveData<String[]> categoryArrayList = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public BottomSheetCategoryViewModel(StatisticsUseCaseFacade statisticsUseCaseFacade) {
        this.statisticsUseCaseFacade = statisticsUseCaseFacade;
    }


    public LiveData<Integer> getAccuracy(String category) {
        //TODO: Implement this method
        return null;
    }

    public LiveData<Integer> getLivesEarned(String category) {
        //TODO: Implement this method
        return null;
    }

    public LiveData<Integer> getQuizzesTaken(String category) {
        //TODO: Implement this method
        return null;
    }

    private void fetchCategoryData() {
        String[] categories = categoryArrayList.getValue();
    }

    public String getChapterDescription(String category, int chapter) {
        int descriptionArrayResId = getChapterDescriptionResId(category);
        return "";
    }

    public String getChapterTitle(String category, int chapter) {
        return ""; // Or handle the case where the chapter is not found
    }


    private int getChapterDescriptionResId(String category) {
        for (int i = 0; i < Objects.requireNonNull(categoryArrayList.getValue()).length; i++) {
            if (categoryArrayList.getValue()[i].equals(category)) {
                return com.audioquiz.designsystem.R.array.mixing_descriptions;
            }
        }
        return 0;
    }


}
