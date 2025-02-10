package com.audioquiz.feature.home.presentation.viewmodel;


import android.os.Bundle;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audioquiz.core.domain.usecase.user.stats.StatisticsUseCaseFacade;
import com.audioquiz.core.model.user.stats.CategoryStats;
import com.audioquiz.core.model.user.stats.CategoryStatsData;
import com.audioquiz.designsystem.base.SingleLiveEvent;
import com.audioquiz.designsystem.util.CategoryVisualItem;
import com.audioquiz.feature.home.domain.HomeViewContract;
import com.audioquiz.feature.home.navigation.HomeCoordinatorEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";
    private final StatisticsUseCaseFacade statisticsUseCaseFacade;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final SingleLiveEvent<HomeCoordinatorEvent> _coordinatorEvent = new SingleLiveEvent<>();
    private final MutableLiveData<HomeViewContract.State> viewState = new MutableLiveData<>();
    private final MutableLiveData<HomeViewContract.Effect> effect = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private final MutableLiveData<List<HomeViewContract.CategoryUi>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Pair<List<HomeViewContract.CategoryUi>, List<Integer>>> updatedCategoriesLiveData = new MutableLiveData<>();

    @Inject
    public HomeViewModel(StatisticsUseCaseFacade statisticsUseCaseFacade) {
        this.statisticsUseCaseFacade = statisticsUseCaseFacade;
        viewState.setValue(createInitialState());
        fetchAndUpdateCategoryData();
    }

    //region LIVEDATA
    // GETTERS

    public LiveData<HomeCoordinatorEvent> navigationEvent() {
        return _coordinatorEvent;
    }
    public LiveData<HomeViewContract.State> viewState() {
        return viewState;
    }
    public LiveData<HomeViewContract.Effect> viewEffects() {
        return effect;
    }
    public LiveData<List<HomeViewContract.CategoryUi>> getCategoryListLiveData() {
        return categoriesLiveData;
    }
    public LiveData<Pair<List<HomeViewContract.CategoryUi>, List<Integer>>> getUpdatedCategoriesLiveData() {
        return updatedCategoriesLiveData;
    }
    //endregion

    //region STATE
    private HomeViewContract.State createInitialState() {
        HomeViewContract.State initialState = new HomeViewContract.State(false, createDefaultCategoryUiList(), false, false);
        Timber.tag(TAG).d("createInitialState called");
        return initialState;
    }

    private List<HomeViewContract.CategoryUi> createDefaultCategoryUiList() {
        List<HomeViewContract.CategoryUi> categoryUiList = new ArrayList<>();
        for (CategoryVisualItem visualItem : CategoryVisualItem.values()) {
            HomeViewContract.CategoryUi categoryUi = new HomeViewContract.CategoryUi(
                    visualItem.ordinal(), // Use enum ordinal as index
                    visualItem.name(),    // Use enum name as category name
                    1,                              // Default currentChapter
                    visualItem.getIcon(),
                    visualItem.getBadge(),
                    visualItem.getDescriptionArray(),
                    visualItem.getChapterNamesArray()
            );
            categoryUiList.add(categoryUi);
        }
        return categoryUiList;
    }

    private void fetchAndUpdateCategoryData() {
        disposables.add(
                statisticsUseCaseFacade.getCategoryStats()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(this::updateCategoryUiList)
                        .subscribe(
                                categoryListPair -> {
                                    updatedCategoriesLiveData.postValue(categoryListPair);
                                    HomeViewContract.State currentState = viewState.getValue();
                            if (currentState != null) {
                                 viewState.setValue(new HomeViewContract.State(currentState.isLoading(), categoryListPair.first, currentState.isShowBadges(), currentState.isBottomSheetVisible()));
                            }
                        }, error -> {
                                    Timber.tag(TAG).e(error, "Error loading category data");
                            // Handle error (e.g., show error message in viewState)
                        })
        );
    }

    private Pair<List<HomeViewContract.CategoryUi>, List<Integer>> updateCategoryUiList(CategoryStats categoryStats) {
        List<HomeViewContract.CategoryUi> updatedCategories = new ArrayList<>();
        List<Integer> removedPositions = new ArrayList<>(); // Keep track of removed positions
        int i = 0; // Index to track the original position
        for (CategoryStatsData categoryStatsData : categoryStats.getAllCategoryStatsData().values()) {
            String categoryName = categoryStatsData.getCategoryName();
            if (categoryName.equals("Interval") || categoryName.equals("Pitch")) {
                // Skip Interval and Pitch categories
                removedPositions.add(i);
            } else {
                try {
                    CategoryVisualItem visualItem = CategoryVisualItem.valueOf(categoryStatsData.getCategoryName()); // Safe to assume valid name here
                    HomeViewContract.CategoryUi categoryUi = new HomeViewContract.CategoryUi(
                            visualItem.ordinal(),
                            visualItem.name(),
                            categoryStatsData.getCurrentChapter(),
                            visualItem.getIcon(),
                            visualItem.getBadge(),
                            visualItem.getDescriptionArray(),
                            visualItem.getChapterNamesArray()
                    );
                    i++; // Increment index only if category is valid

                    updatedCategories.add(categoryUi);
                } catch (IllegalArgumentException e) {
                    Timber.tag(TAG).e(e, "Invalid category name: %s", categoryName);
                    removedPositions.add(i);
                }
            }
        }

        // Rearrange the updatedCategories list based on CategoryVisualItem enum order
        List<HomeViewContract.CategoryUi> sortedCategories = new ArrayList<>();
        for (CategoryVisualItem visualItem : CategoryVisualItem.values()) {
            for (HomeViewContract.CategoryUi categoryUi : updatedCategories) {
                if (categoryUi.name.equals(visualItem.name())) {
                    sortedCategories.add(categoryUi);
                    break; // Move to the next visualItem once found
                }
            }
        }

        return new Pair<>(sortedCategories, removedPositions);
    }

    private HomeViewContract.CategoryUi getCategoryByName(String name) {
        HomeViewContract.State currentState = viewState.getValue();if (currentState != null && currentState.getCategories() != null) {
            for (HomeViewContract.CategoryUi categoryUi : currentState.getCategories()) {
                if (categoryUi.name.equals(name)) {
                    return categoryUi;
                }
            }
        }
        return null; // Or handle the case where the category is not found
    }

    //endregion

    //region EVENTS
    public void process(HomeViewContract.Event event) {
        Timber.tag(TAG).d("process called with event: %s", event);
        if (event instanceof HomeViewContract.Event.OnCategoryCardClicked onCategoryCardClicked) {
            effect.setValue(new HomeViewContract.Effect.ShowCategoryBottomSheet(onCategoryCardClicked.getCategory(), onCategoryCardClicked.getCurrentChapter()));
        } else if (event instanceof HomeViewContract.Event.OnSettingsButtonClicked) {
            sendCoordinatorEvent(new HomeCoordinatorEvent.OnSettingsButtonPressed());
        } else if (event instanceof HomeViewContract.Event.OnViewBadgesButtonClicked) {
            handleViewBadgesButtonClicked();
        } else if (event instanceof HomeViewContract.Event.OnStartQuizTriggered startQuizTriggered) {
            sendCoordinatorEvent(new HomeCoordinatorEvent.OnStartQuizClicked(startQuizTriggered.getBundle()));
        }
    }

    private void handleCategoryClicked(HomeViewContract.Event.OnCategoryCardClicked event) {
        Timber.tag(TAG).d("handleCategoryClicked called with event: %s", event + event.getCategory() + event.getCurrentChapter());

    }

    private void handleViewBadgesButtonClicked() {
        // You can toggle badges or show a dialog here
        HomeViewContract.State currentState = viewState.getValue();
        if (currentState != null) {
            HomeViewContract.State updatedState = new HomeViewContract.State(false, currentState.getCategories(), !currentState.isShowBadges(), currentState.isBottomSheetVisible());
             viewState.setValue(updatedState);
        }
    }

    protected <E extends HomeCoordinatorEvent> void sendCoordinatorEvent(E event) {
        Timber.tag(TAG).d("sending coordinatorEvent: %s", event);
        _coordinatorEvent.postValue(event);
    }
    //endregion

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}