package com.audioquiz.feature.home.presentation.viewmodel;


import android.util.Log;
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
        HomeViewContract.State initialState = new HomeViewContract.State(false, createDefaultCategoryUiList(), false);
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
                                 viewState.setValue(new HomeViewContract.State(currentState.isLoading, categoryListPair.first, currentState.showBadges));
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

        return new Pair<>(updatedCategories, removedPositions);
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
        if (event instanceof HomeViewContract.Event.CategoryClicked categoryClicked) {
            handleCategoryClicked(categoryClicked);
        } else if (event instanceof HomeViewContract.Event.OnSettingsButtonClicked) {
            sendCoordinatorEvent(new HomeCoordinatorEvent.OnSettingsButtonPressed());
        } else if (event instanceof HomeViewContract.Event.OnViewBadgesButtonClicked) {
            handleViewBadgesButtonClicked();
        }
    }

    private void handleCategoryClicked(HomeViewContract.Event.CategoryClicked event) {
        Timber.tag(TAG).d("handleCategoryClicked called with event: %s", event);
        HomeViewContract.CategoryUi categoryUi = getCategoryByName(event.getCategory());
        if (categoryUi != null) {
            effect.setValue(new HomeViewContract.Effect.ShowCategoryBottomSheet(categoryUi.name, categoryUi.currentChapter));
        } else {
            effect.setValue(new HomeViewContract.Effect.ShowErrorToast("CategoryUi not found"));
        }
    }

    private void handleViewBadgesButtonClicked() {
        // You can toggle badges or show a dialog here
        HomeViewContract.State currentState = viewState.getValue();
        if (currentState != null) {
            HomeViewContract.State updatedState = new HomeViewContract.State(false, currentState.categories, !currentState.showBadges);
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

/*


    private Single<List<HomeViewContract.CategoryUi>> mapFromDomain(Single<CategoryStats> categoryStats) {
        return categoryStats.map(categoryStats1 ->
                categoryStats1.getAllCategoryStatsData().values().stream()
                        .map(data -> UiMapper.mapToUi(data, HomeViewContract.CategoryUi.class))
                        .collect(Collectors.toList()));
    }

    private Single<CategoryStatsDataUi> mapFromDomainData(Single<CategoryStatsData> categoryStatsData) {
        return categoryStatsData.map(data -> UiMapper.mapToUi(data, CategoryStatsDataUi.class));
    }


    public Single<List<HomeViewContract.CategoryUi>> getCategoryList() {
        return mapFromDomain(statisticsUseCaseFacade.getCategoryStats());
    }

    public Single<Integer> getCurrentChapterSingle(String category) {
        int index = getCategoryIndex(category);
        if (index != -1) {
            return getCategoryList().map(categoryList -> categoryList.get(index).getCurrentChapter());
        } else {
            return Single.error(new IllegalArgumentException("CategoryUi not found")); // Handle invalid category
        }
    }

    private int getCategoryIndex(String category) {
        if (categoryIndicesMap.getValue() != null) {
            Map<String, Integer> categoryMap = categoryIndicesMap.getValue();
            Integer index = categoryMap.get(category); // Store the value in a variable
            if (index != null) {
                return index; // Return the stored value
            }
        }
        Log.e(TAG, "CategoryUi not found");
        return -1;
    }


    private HomeViewContract.CategoryUi mapToCategoryUi(CategoryStatsData stats) {
        return new HomeViewContract.CategoryUi(
                stats.getCategoryIndex(),
                stats.getCategoryName(),
                stats.getCurrentChapter(),
                Objects.requireNonNull(imageResourcesMap.getValue()).getOrDefault(stats.getCategoryName(), -1),
                Objects.requireNonNull(badgeResourcesMap.getValue()).getOrDefault(stats.getCategoryName(), -1),
                Objects.requireNonNull(descriptionsMap.getValue()).getOrDefault(stats.getCategoryName(), -1),
                Objects.requireNonNull(chaptersMap.getValue()).getOrDefault(stats.getCategoryName(), -1));
    }

    private HomeViewContract.CategoryUi mapToSheetCategoryUi(CategoryStatsData stats) {
        return new HomeViewContract.CategoryUi(
                stats.getCategoryIndex(),
                stats.getCategoryName(),
                stats.getCurrentChapter(),
                Objects.requireNonNull(imageResourcesMap.getValue()).getOrDefault(stats.getCategoryName(), -1),
                Objects.requireNonNull(badgeResourcesMap.getValue()).getOrDefault(stats.getCategoryName(), -1),
                Objects.requireNonNull(descriptionsMap.getValue()).getOrDefault(stats.getCategoryName(), -1),
                Objects.requireNonNull(chaptersMap.getValue()).getOrDefault(stats.getCategoryName(), -1));
    }

    private List<HomeViewContract.CategoryUi> mapToCategoryUiList(CategoryStats stats) {
        Map<String, CategoryStatsData> categoryMap = stats.getAllCategoryStatsData();
        List <HomeViewContract.CategoryUi> categoryUiList = new ArrayList<>();
        for (Map.Entry<String, CategoryStatsData> entry : categoryMap.entrySet()) {
            categoryUiList.add(mapToCategoryUi(entry.getValue()));
        }
        return categoryUiList;
    }


    private void setResources(String[] categories, TypedArray categoryImageIds, TypedArray categoryBadgesId, TypedArray categoryDescriptions, TypedArray categoryChapters) {
        for (int i = 0; i < categories.length; i++) {
            categoryIndicesMap.postValue(getDataMap(categories[i], i));
            imageResourcesMap.postValue(getDataMap(categories[i], categoryImageIds.getResourceId(i, -1)));
            badgeResourcesMap.postValue(getDataMap(categories[i], categoryBadgesId.getResourceId(i, -1)));
            descriptionsMap.postValue(getDataMap(categories[i], categoryDescriptions.getResourceId(i, -1)));
            chaptersMap.postValue(getDataMap(categories[i], categoryChapters.getResourceId(i, -1)));
            Log.d(TAG, "setResources: " + categories[i]);
        }
    }

    private Map<String, Integer> getDataMap(String key, int value) {
        Map<String, Integer> map = new HashMap<>();
        if (key == null) {
            Log.e(TAG, "Key is null");
            return map;
        }
        Log.d(TAG, "Key: " + key + ", Value: " + value);
        map.put(key, value);
        return map;
    }



private List<HomeViewContract.CategoryUi> getCategoryUiList(CategoryStats categoryData) {
        Log.d(TAG, "getCategoryUiList called");
        List<HomeViewContract.CategoryUi> categoryUiList = new ArrayList<>();
        int i = 0;
        for (CategoryStatsData categoryStatsData : categoryData.getAllCategoryStatsData().values()) {
            // Assuming getCurrentChapter is now non-blocking or returns a Single
            categoryUiList.add(getNewCategory(i, categoryStatsData.getCategoryName(), categoryStatsData.getCurrentChapter()));
            i++;
        }
        return categoryUiList;
    }

    private HomeViewContract.CategoryUi getNewCategory(int i, String category, int currentChapter) {
        return new HomeViewContract.CategoryUi(
                i,
                category,
                currentChapter,
                getResourceId(category, imageResourcesMap),
                getResourceId(category, badgeResourcesMap),
                getResourceId(category, descriptionsMap),
                getResourceId(category, chaptersMap));
    }

    private Integer getResourceId(String category, MutableLiveData<Map<String, Integer>> map) {
        Integer id = 0;
        Map<String, Integer> resourceMap = map.getValue();
        if (resourceMap != null && resourceMap.containsKey(category)) {
            Log.d(TAG, "getResourceId: " + resourceMap.get(category));
            id = resourceMap.get(category);
        }
        return id;
    }




    public void init(String[] categories, TypedArray categoryImageIds, TypedArray categoryBadgesId, TypedArray categoryDescriptions, TypedArray categoryChapters) {
        Log.d(TAG, "init called");
        setResources(categories, categoryImageIds, categoryBadgesId, categoryDescriptions, categoryChapters);
        Log.d(TAG, "init called2");
        HomeViewContract.State initialState = createInitialState();

        Disposable categoryStatsDisposable = statisticsUseCaseFacade.getCategoryStats()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(categoryData -> { // Example using flatMap
                    Log.d(TAG, "Received categoryData: " + categoryData);
                    return Single.just(getCategoryUiList(categoryData)); // Emit the categoryUiList
                })
                .subscribe(categoryList -> {
                    initialState.categories = categoryList;
                    initialState.showBadges = false;
                    viewState.setValue(initialState);
                }, error -> {
                    Log.e(TAG, "Error loading category data", error);
                });

        disposables.add(categoryStatsDisposable);
    }


    public void setInitialState() {
        disposables.add(
                getCategoryList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(categories -> {
                            HomeViewContract.State initialState = new HomeViewContract.State(false, categories, false);
                            viewState.setValue(initialState);
                        }, Throwable::printStackTrace)
        );
    }

    private void fetchCategoryData() {
        String[] categories = categoryArrayList.getValue();
        if (categories == null) return;
        for (String category : categories) {
            disposables.add(
                    getCurrentChapterSingle(category)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    currentChapter -> updateCategoryProgress(category, currentChapter),
                                    Throwable::printStackTrace
                            )
            );
        }
    }
    private void updateCategoryProgress(String category, int currentChapter) {
        HomeViewContract.State currentState = viewState.getValue();
        if (currentState != null) {
            currentState.categories = currentState.categories.stream()
                    .map(cat -> cat.name.equals(category)
                            ? new HomeViewContract.CategoryUi(
                            cat.categoryIndex,
                            cat.name,
                            currentChapter,
                            cat.imageResId,
                            cat.badgeResId,
                            cat.descriptionResId,
                            cat.chaptersResId)
                            : cat)
                    .collect(Collectors.toList());
            viewState.setValue(currentState);
        }
    }

    private int getCategoryIndex(String category) {
        HomeViewContract.State currentState = viewState.getValue();
        if (currentState != null) {
            List<HomeViewContract.CategoryUi> categories = currentState.categories;
            for (int i = 0; i < categories.size(); i++) {
                if (categories.get(i).name.equals(category)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public String getChapterDescription(String category, int chapter) {
        int descriptionArrayResId = getChapterDescriptionResId(category);
        return "";
    }

    public String getChapterTitle(String category, int chapter) {
        return ""; // Or handle the case where the chapter is not found
    }


//    private final MutableLiveData<int[]> categoryImageResourceIds = new MutableLiveData<>();
//    private final MutableLiveData<int[]> badgeImageResourceIds = new MutableLiveData<>();
//    private final MutableLiveData<int[]> categoryDescriptions = new MutableLiveData<>();
//    private final MutableLiveData<int[]> categoryChapters = new MutableLiveData<>();
   private List<HomeViewContract.CategoryUi> loadCategories() {
        return Arrays.asList(
                new HomeViewContract.CategoryUi(
                        "Sound Waves",
                        getCategoryIndex("Sound Waves"),
                        1,
                        R.drawable.ic_soundwave,
                        R.drawable.ic_badge_soundwaves),

                new HomeViewContract.CategoryUi(
                        "Synthesis",
                        getCategoryIndex("Synthesis"),
                        2,
                        R.drawable.ic_synthesis,
                        R.drawable.ic_badge_synthesis),


                new HomeViewContract.CategoryUi(
                        "Mixing",
                        getCategoryIndex("Mixing"),
                        2,
                        R.drawable.ic_mixing,
                        R.drawable.ic_badge_mixing)
        );
    }

    private int getChapterTitlesResId (String category) {
        String[] categories = categoryArrayList.getValue();
        for (String s : Objects.requireNonNull(categories)) {
            if (s.equals(category)) {
                return R.array.mixing_chapters;
            }
        }
        return 0;
    }

    private int getChapterDescriptionResId(String category) {
        for (int i = 0; i < Objects.requireNonNull(categoryArrayList.getValue()).length; i++) {
            if (categoryArrayList.getValue()[i].equals(category)) {
                return R.array.mixing_descriptions;
            }
        }
        return 0;
    }

    public void setCategories(String[] categories) {
        this.categoryArrayList.setValue(categories);
    }

    public void setCategoryImageResourceIds(TypedArray categoryImageResourceIds) {
        int[] categoryImageResourceIdsArray = new int[categoryImageResourceIds.length()];
        for (int i = 0; i < categoryImageResourceIds.length(); i++) {
            categoryImageResourceIdsArray[i] = categoryImageResourceIds.getResourceId(i, -1);
        }
        this.categoryImageResourceIds.setValue(categoryImageResourceIdsArray);
    }

    public void setCategoryDescriptions(TypedArray categoryDescriptionsArray) {
        int[] categoryDescriptions = new int[categoryDescriptionsArray.length()];
        for (int i = 0; i < categoryDescriptionsArray.length(); i++) {
            categoryDescriptions[i] = categoryDescriptionsArray.getResourceId(i, -1);
        }
        this.categoryDescriptions.setValue(categoryDescriptions);
    }

    public void setCategoryChapters(TypedArray categoryChaptersArray) {
        int[] categoryChapters = new int[categoryChaptersArray.length()];
        for (int i = 0; i < categoryChaptersArray.length(); i++) {
            categoryChapters[i] = categoryChaptersArray.getResourceId(i, -1);
        }
        this.categoryChapters.setValue(categoryChapters);

    }*/
}