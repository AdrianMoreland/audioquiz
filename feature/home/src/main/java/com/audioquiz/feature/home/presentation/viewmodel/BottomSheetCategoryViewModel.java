package com.audioquiz.feature.home.presentation.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audioquiz.core.domain.usecase.resources.SyncStaticResourcesUseCase;
import com.audioquiz.core.domain.usecase.user.stats.StatisticsUseCaseFacade;
import com.audioquiz.core.model.quiz.CategoryStaticData;
import com.audioquiz.feature.home.domain.CategoryViewContract;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class BottomSheetCategoryViewModel extends ViewModel {
    private static final String TAG = "BottomSheetCategoryVM";

    private final StatisticsUseCaseFacade statisticsUseCaseFacade;
    private final SyncStaticResourcesUseCase syncStaticResourcesUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<CategoryStaticData> categoryLiveData = new MutableLiveData<>();
    private final MutableLiveData<String[]> categoryArrayList = new MutableLiveData<>();

    private final MutableLiveData<CategoryViewContract.State> viewState = new MutableLiveData<>();
    private final MutableLiveData<CategoryViewContract.Effect> effect = new MutableLiveData<>();

    @Inject
    public BottomSheetCategoryViewModel(StatisticsUseCaseFacade statisticsUseCaseFacade,
                                        SyncStaticResourcesUseCase syncStaticResourcesUseCase) {
        this.statisticsUseCaseFacade = statisticsUseCaseFacade;
        this.syncStaticResourcesUseCase = syncStaticResourcesUseCase;
        viewState.setValue(new CategoryViewContract.State(false, 1, "", "", "", "", "", "", ""));
    }

    public LiveData<CategoryViewContract.State> viewState() {
        return viewState;
    }

    public LiveData<CategoryViewContract.Effect> viewEffects() {
        return effect;
    }

    public LiveData<CategoryStaticData> getCategoryLiveData() {
        return categoryLiveData;
    }

 /*   public void process(CategoryViewContract.Event event) {
        Timber.tag(TAG).d("process called with event: %s", event);
        if (event instanceof CategoryViewContract.Event.OnStartQuizClicked onStartQuizClicked) {
            sendCoordinatorEvent(new HomeCoordinatorEvent.OnStartQuizClicked(onStartQuizClicked.getBundle()));
        }  else {
            Timber.tag(TAG).e("Unknown event: %s", event);
        }
    }

    protected <E extends HomeCoordinatorEvent> void sendCoordinatorEvent(E event) {
        Timber.tag(TAG).d("sending coordinatorEvent: %s", event);
        _coordinatorEvent.postValue(event);
    }*/


    public LiveData<Integer> getAccuracy(String category) {
        //TODO: Implement this method
        MutableLiveData<Integer> accuracy = new MutableLiveData<>();
        accuracy.setValue(50);
        return accuracy;
    }

    public LiveData<Integer> getLivesEarned(String category) {
        //TODO: Implement this method
        MutableLiveData<Integer> livesEarned = new MutableLiveData<>();
        livesEarned.setValue(3);
        return livesEarned;
    }

    public LiveData<Integer> getQuizzesTaken(String category) {
        //TODO: Implement this method
        MutableLiveData<Integer> quizzesTaken = new MutableLiveData<>();
        quizzesTaken.setValue(0);
        return quizzesTaken;
    }

    public Single<CategoryStaticData> getCategoryData(Integer categoryIndex) {
        return syncStaticResourcesUseCase.getCategoryData(categoryIndex);
    }

    public void loadCategory(Integer categoryId) {
        disposables.add(
                syncStaticResourcesUseCase.getCategoryData(categoryId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<CategoryStaticData, CategoryViewContract.State>() {
                            @Override
                            public CategoryViewContract.State apply(CategoryStaticData data) throws Exception {
                                // Map the raw data to UI state.
                                return new CategoryViewContract.State(
                                        false,
                                        categoryId,
                                        data.getCategoryName(),
                                        data.getChapters().get(0),
                                        data.getChapters().get(1),
                                        data.getChapters().get(2),
                                        data.getDescriptions().get(0),
                                        data.getDescriptions().get(1),
                                        data.getDescriptions().get(2)
                                );
                            }
                        })
                        .subscribe(new Consumer<CategoryViewContract.State>() {
                            @Override
                            public void accept(CategoryViewContract.State state) throws Exception {
                                // Update the LiveData so that data binding in the fragment updates the UI.
                                viewState.setValue(state);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                // Handle errors here (e.g., log the error or update the UI with an error state).
                            }
                        })
        );
    }

    public void loadCategoryData(String categoryName) {
        Integer index = getCategoryIndex(categoryName.toLowerCase());
        Disposable disposable = syncStaticResourcesUseCase.getCategoryData(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryLiveData::setValue, // onNext
                        throwable -> Timber.tag(TAG).e(throwable, "Error loading category: " + categoryName + ", at index: " + index)
                );
        disposables.add(disposable);
    }

    private Integer getCategoryIndex(String categoryName) {
        switch (categoryName) {
            case "soundwaves" -> {
                return 0;
            }
            case "synthesis" -> {
                return 1;
            }
            case "production" -> {
                return 2;
            }
            case "mixing" -> {
                return 3;
            }
            case "processing" -> {
                return 4;
            }
            case "musictheory" -> {
                return 5;
            }
            default -> {
                return null;
            }
        }
    }

    public String getChapterDescription(String category, int chapter) {
        //    int descriptionArrayResId = getChapterDescriptionResId(category);
        return "Description"; // Or handle the case where the chapter is not found
    }

    public String getChapterTitle(String category, int chapter) {
        return "Chapter"; // Or handle the case where the chapter is not found
    }


    private int getChapterDescriptionResId(String category) {
        for (int i = 0; i < Objects.requireNonNull(categoryArrayList.getValue()).length; i++) {
            if (categoryArrayList.getValue()[i].equals(category)) {
                return com.audioquiz.designsystem.R.array.mixing_descriptions;
            }
        }
        return 0;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear(); // Dispose of all subscriptions
    }
}
