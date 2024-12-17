package com.audioquiz.feature.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.audioquiz.designsystem.model.ToolbarData;
import com.audioquiz.feature.home.R;
import com.audioquiz.feature.home.adapter.CategoryAdapter;
import com.audioquiz.feature.home.databinding.FragmentHomeBinding;
import com.audioquiz.feature.home.domain.HomeViewContract;
import com.audioquiz.feature.home.navigation.HomeCoordinatorEvent;
import com.audioquiz.feature.home.navigation.HomeFlowCoordinator;
import com.audioquiz.feature.home.presentation.viewmodel.HomeViewModel;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import timber.log.Timber;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private HomeViewModel homeViewModel;
    private CategoryAdapter adapter;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;

    @Inject
    HomeFlowCoordinator homeFlowCoordinator;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(TAG).d("onCreate called");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setViewModel(homeViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        setupRecyclerView(binding.recyclerView);

        binding.setToolbarData(new ToolbarData(true, "Home"));

        Timber.tag(TAG).d("onCreateView called with binding: %s", binding);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        Timber.tag(TAG).d("onViewCreated called");
        setupViewBadgesButton();
        initializeComponents();
        observeViewModel();
        Timber.tag(TAG).d("onViewCreated done");
    }

    private void setupRecyclerView(RecyclerView view) {
        recyclerView = view;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new CategoryAdapter(new ArrayList<>(), category ->
                homeViewModel.process(new HomeViewContract.Event.CategoryClicked(category.getName(), category.getCurrentChapter())));
        recyclerView.setAdapter(adapter);
     }

    public void initializeComponents() {
        binding.btnSettings.setOnClickListener(v -> homeViewModel.process(new HomeViewContract.Event.OnSettingsButtonClicked()));
        binding.btnViewBadges.setOnClickListener(v -> homeViewModel.process(new HomeViewContract.Event.OnViewBadgesButtonClicked()));
       Timber.tag(TAG).d("initializeComponents called");
    }

    private void observeViewModel() {
        homeViewModel.viewState().observe(getViewLifecycleOwner(), this::onViewStateChanged);
        homeViewModel.viewEffects().observe(getViewLifecycleOwner(), this::onViewEffectReceived);
        homeViewModel.navigationEvent().observe(getViewLifecycleOwner(), this::handleNavigation);
        homeViewModel.getUpdatedCategoriesLiveData().observe(getViewLifecycleOwner(), pair -> adapter.updateCategories(pair.first, pair.second));
       Timber.tag(TAG).d("observeViewModel called");
    }

    private void onViewStateChanged(HomeViewContract.State state) {
        if (state != null) {
            if (state.isShowBadges()) {
                binding.frameIconsCompleted.setVisibility(View.VISIBLE);
            } else {
                binding.frameIconsCompleted.setVisibility(View.GONE);
            }
           Timber.tag(TAG).d("onViewStateChanged called");
        }
    }

    private void onViewEffectReceived(HomeViewContract.Effect effect) {
        if (effect != null) {
            if (effect instanceof HomeViewContract.Effect.ShowCategoryBottomSheet bottomSheetEffect) {
                showCategoryBottomSheet(bottomSheetEffect.getCategory(), bottomSheetEffect.getCurrentChapter());
            } else if (effect instanceof HomeViewContract.Effect.ShowErrorToast errorToastEffect) {
                Toast.makeText(getContext(), errorToastEffect.getError(), Toast.LENGTH_SHORT).show();
            }
        }
        Timber.tag(TAG).d("onViewEffectReceived called");
    }

    private void handleNavigation(HomeCoordinatorEvent event) {
        if (homeFlowCoordinator != null) {
            homeFlowCoordinator.onEvent(event);
        } else {
           Timber.tag(TAG).e("homeFlowCoordinator is null");
        }
    }

    private void setupViewBadgesButton() {
        LinearLayout frameIconsCompleted = binding.frameIconsCompleted;
        frameIconsCompleted.setVisibility(View.GONE);

        binding.btnViewBadges.setOnClickListener(v -> {
            int visibility = frameIconsCompleted.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            frameIconsCompleted.setVisibility(visibility);
            Timber.tag(TAG).d(visibility == View.VISIBLE ? "Showing the badges" : "Hiding the badges");
        });
        Timber.tag(TAG).d("setupViewBadgesButton called");
    }

    private void showCategoryBottomSheet(String category, Integer currentChapter) {
       Timber.tag(TAG).d("Showing bottom sheet for " + category + ", chapter: " + currentChapter);
       if (currentChapter == 0) {
           currentChapter = 1;
           Timber.tag(TAG).e("Current chapter is 0 for " + category);
       }
        if (currentChapter != 0) {
            BottomSheetCategoryFragment bottomSheetFragment = new BottomSheetCategoryFragment();
            Bundle args = new Bundle();
            args.putString("category", category);
            args.putInt("current_chapter", currentChapter); // Pass the current chapter
            bottomSheetFragment.setArguments(args);
            bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
        } else {
           Timber.tag(TAG).e("Error: Current chapter is 0");
        }
       Timber.tag(TAG).d("showCategoryBottomSheet called");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //region TRASH
    // Ensure that cardViewIdsArray and progressBarsIdArray have the same size


 /*       private void initializeViews(HomeViewContract.State state, String category, int currentChapter) {
        int chapter = (int) currentChapter;
        if (chapter == 0) {
            chapter = 1;
            Log.e(TAG, "Current chapter is 0 for " + category);
        }

        if (state.getCategories() != null) {
            for (HomeViewContract.CategoryUi categoryUi : state.getCategories()) {
                if (categoryUi.name.equals(category)) {
                    Log.d(TAG, "Found category: " + category);

                    // Access properties from categoryUi (progress, isCompleted, badgeResId)
                    // ...

                    int cardViewId = cardViewIdsArray.getResourceId(i, -1);
                    CardView cardView = findCardViewForCategory(categoryName);

                    CardView cardView = binding.getRoot().findViewById(cardViewId);

                    if (cardView != null) {
                        cardView.setOnClickListener(v -> showCategoryBottomSheet(category, currentChapter));
                        LinearProgressIndicator progressBar = binding.getRoot().findViewById(progressBarsIdArray.getResourceId(i, -1));
                        ImageView imageBadge = binding.getRoot().findViewById(imageBadgesIdArray.getResourceId(i, -1));
                        updateProgressBar(category, currentChapter, progressBar, imageBadge);
                    }

                    if (cardView != null) {
                        Integer finalCurrentChapter = chapter;
                        cardView.setOnClickListener(v -> showCategoryBottomSheet(category, finalCurrentChapter));

                    } else {
                        Log.e(TAG, "CardView not found for index: " + i);
                    }
                }
            }
        }
        setupViewBadgesButton();
    }

    private CardView findCardViewForCategory(String categoryName) {
        String cardViewId = "cardView_" + categoryName.toLowerCase();
        int resId = getResources().getIdentifier(cardViewId, "id", requireContext().getPackageName());
        return binding.getRoot().findViewById(resId);
    }

    private void updateProgressBar(String categoryName, int currentChapter, LinearProgressIndicator progressBar, ImageView imageBadge) {
        HomeViewContract.CategoryUi categoryUi = getCategoryUiByName(categoryName); // Implement this method
        if (categoryUi != null && progressBar != null && currentChapter != 0) {
        progressBar.setProgress(categoryUi.getProgress());
        if (categoryUi.isCompleted()) {
            imageBadge.setBackgroundResource(categoryUi.badgeResId);
        }
    } else {
        // Handle case where categoryUi or progress bar is not found
    }
}

    private int getCategoryIndex(String category) {
        int index = Arrays.asList(categoriesList).indexOf(category);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
        Log.d(TAG, "getCategoryIndex called for category: " + category + ", index: " + index);
        return index;
    }

      private void setupBlurView() {
        float radius = 10f;
        if (isAdded()) {
            View decorView = requireActivity().getWindow().getDecorView();
            ViewGroup rootView = decorView.findViewById(android.R.id.content);
            Drawable windowBackground = decorView.getBackground();
            BlurView blurView = binding.blurView;
            blurView.setupWith(rootView, new RenderScriptBlur(requireActivity())).setFrameClearDrawable(windowBackground).setBlurRadius(radius);
            blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
            blurView.setClipToOutline(true);
        }

    }*/

   /*        private void setupCategoryViews(List<HomeViewContract.CategoryUi> categories) {
        Log.d(TAG, "setupCategoryViews called");
        for (HomeViewContract.CategoryUi categoryUi : categories) {
            initializeViews(categoryUi.name, categoryUi.currentChapter);
        }
        Log.d(TAG, "setupCategoryViews done");
    }


 if (
            //    cardViewIdsArray.length() != categoriesList.length ||
                        progressBarsIdArray.length() != categoriesList.length) {
            Log.e(TAG, "CardView and ProgressBar arrays do not match the categories list size");
            return;
        }


    private void initializeViews(String category, Object currentChapter) {
        int chapter = (int) currentChapter;
        if (chapter == 0) {
            chapter = 1;
            Log.e(TAG, "Current chapter is 0 for " + category);
        }

        // Ensure that cardViewIdsArray and progressBarsIdArray have the same size
        if (
            //    cardViewIdsArray.length() != categoriesList.length ||
                        progressBarsIdArray.length() != categoriesList.length) {
            Log.e(TAG, "CardView and ProgressBar arrays do not match the categories list size");
            return;
        }

        for (int i = 0; i < categoriesList.length; i++) {
            if (categoriesList[i].equals(category)) {
                int cardViewId = cardViewIdsArray.getResourceId(i, -1);
                CardView cardView = binding.getRoot().findViewById(cardViewId);
                if (cardView != null) {
                    Integer finalCurrentChapter = chapter;
                    cardView.setOnClickListener(v -> showCategoryBottomSheet(category, finalCurrentChapter));
                    LinearProgressIndicator progressBar = binding.getRoot().findViewById(progressBarsIdArray.getResourceId(i, -1));
                    ImageView imageBadge = binding.getRoot().findViewById(imageBadgesIdArray.getResourceId(i, -1));
                    updateProgressBar(category, finalCurrentChapter, progressBar, imageBadge);
                } else {
                    Log.e(TAG, "CardView not found for index: " + i);
                }
            }
        }
        setupViewBadgesButton();
    }

       private void setupNavigationButtons(View view) {
        View bottomNavbar = view.findViewById(R.id.bottom_navbar_frame);
        // Set up the navigation buttons
        MaterialButton imageButton = view.findViewById(R.id.btn_settings);
        imageButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_settingsFragment));
        setupNavigationButton(view, R.id.btn_settings, R.id.action_homeFragment_to_settingsFragment);
        setupNavigationButton(bottomNavbar, R.id.btn_stats, R.id.action_homeFragment_to_userStatisticsFragment);
        setupNavigationButton(bottomNavbar, R.id.btn_rank, R.id.action_homeFragment_to_rankFragment);
        setupNavigationButton(bottomNavbar, R.id.btn_play, R.id.action_homeFragment_to_playFragment);
    }

    private void setupNavigationButton(View view, int buttonId, int actionId) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(v -> {
            // Disable the button
            button.setEnabled(false);
            Navigation.findNavController(view).navigate(actionId);
            // Re-enable the button after 1 second
            new Handler().postDelayed(() -> button.setEnabled(true), 1000); // Delay in milliseconds
        });
    }

     @Override
    protected int getNavGraphActionId() {
        return R.id.action_homeFragment_to_settingsFragment;
    }

    @Override
    protected int getHomeToStatsActionId() {
        return R.id.action_homeFragment_to_userStatisticsFragment;
    }

    @Override
    protected int getHomeToRankActionId() {
        return R.id.action_homeFragment_to_rankFragment;
    }

    @Override
    protected int getHomeToPlayActionId() {
        return R.id.action_homeFragment_to_playFragment;
    }



 // in oncreate:
 getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            if (event == Lifecycle.Event.ON_START) {
                homeViewModel.getUserStatistics();
            }
        });

         homeViewModel.getUserStatisticsLiveData().observe(getViewLifecycleOwner(), userStatistics -> {
            if (userStatistics != null) {
                findAndInitializeViews(view);
            } else {
                Log.e(TAG, "com.example.model.UserStatsDto is null");
            }
        });

         MaterialButton buttonDeleteMockData = view.findViewById(R.id.btn_settings);
        buttonDeleteMockData.setOnClickListener(v -> deleteAllUserStatistics());

       MaterialButton buttoninsertusserMockData = view.findViewById(R.id.btn_settings);
       buttoninsertusserMockData.setOnClickListener(v -> generateUserMockValues());

        MaterialButton buttonGenerateMockData = view.findViewById(R.id.btn_rank);
        buttonGenerateMockData.setOnClickListener(v -> generateAndUploadMockEntries());

 private int fetchCurrentChapter(String category) {
        int currentChapter = homeViewModel.getCurrentChapter(category);
        Log.d(TAG, "Current chapter for " + category + ": " + currentChapter);
        if (currentChapter == 0) {
            return 1;
        }
        return currentChapter;
    }

    private void findAndInitializeViews(View view) {
        // Find the views and set the onClickListeners
        for (int i = 0; i < cardViewIdsArray.length(); i++) {
            int cardViewId = cardViewIdsArray.getResourceId(i, -1);
            CardView cardView = view.findViewById(cardViewId);
            if (cardView != null) {
                final String category = categoriesList[i];
                cardView.setOnClickListener(v -> showBottomSheetDialog(category));
                LinearProgressIndicator progressBar = view.findViewById(progressBarsIdArray.getResourceId(i, -1));
                ImageView imageBadge = view.findViewById(imageBadgesIdArray.getResourceId(i, -1));
                updateProgressBar(category, progressBar, imageBadge);
            } else {
                Log.e(TAG, "CardView not found for index: " + i);
            }
        }

        MaterialButton viewBadgesButton = view.findViewById(R.id.btn_view_badges);
        // Get a reference to the LinearLayout
        LinearLayout frameIconsCompleted = view.findViewById(R.id.frame_icons_completed);

        // Set an OnClickListener to the LinearLayout
        viewBadgesButton.setOnClickListener(v -> {
            // Toggle visibility of the LinearLayout
            if (frameIconsCompleted.getVisibility() == View.VISIBLE) {
                frameIconsCompleted.setVisibility(View.GONE);
            } else {
                frameIconsCompleted.setVisibility(View.VISIBLE);
            }
        });
    }


    // Method to show the BottomSheetFragment
    public void showBottomSheetDialog2(String category) {
        int currentChapter = fetchCurrentChapter(category);
        Log.d(TAG, "Showing bottom sheet for " + category + ", chapter: " + currentChapter);
        if (currentChapter != 0) {
            BottomSheetCategoryFragment bottomSheetFragment = new BottomSheetCategoryFragment();
            Bundle args = new Bundle();
            args.putString("category", category);
            args.putInt("current_chapter", currentChapter); // Pass the current chapter

            bottomSheetFragment.setArguments(args);
            bottomSheetFragment.show(getParentFragmentManager(), bottomSheetFragment.getTag());
        } else {
            Log.e(TAG, "Error: Current chapter is 0");
        }
    }


    private void setupNavigationButton2(View view, int buttonId, int actionId) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(v -> {
            // Disable the button
            button.setEnabled(false);
            Navigation.findNavController(view).navigate(actionId);
            // Re-enable the button after 1 second
            new Handler().postDelayed(() -> button.setEnabled(true), 1000); // Delay in milliseconds
        });
    }


    private void updateProgressBar2(String category, LinearProgressIndicator progressBar, ImageView imageBadge) {
        if (progressBar != null) {
            int currentChapter = fetchCurrentChapter(category);
            Log.d(TAG, "Updating progress bar for " + category + ", chapter: " + currentChapter);
            if (currentChapter != 0) {
                final float progress = ((float) (currentChapter - 1) / 3) * 100;
                progressBar.setProgress((int) progress);
                if (progress == 100) {
                    TypedArray badgeResources = getResources().obtainTypedArray(R.array.badgeResources);
                    try {
                        int badgeResourceId = badgeResources.getResourceId(getCategoryIndex(category), -1);
                        imageBadge.setBackgroundResource(badgeResourceId);
                    } finally {
                        badgeResources.recycle();
                    }
                }
            }
        }
    }

    private int getCategoryIndex2(String category) {
        // Here, map your category to its index in the badgeResources array
        int index = Arrays.asList(categoriesList).indexOf(category);
        if (index == -1) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
        return index;
    }

  private int getCategoryIndex(String category) {
        // Here, map your category to its index in the badgeResources array
            int index = Arrays.asList(categoriesList).indexOf(category);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid category: " + category);
            }
            return index;
        }

    public void updateProgressBars() {
        updateProgressBar("SoundWaves", soundWavesProgressBar, imageBadge1);
        updateProgressBar("Synthesis", synthesisProgressBar, imageBadge2);
        updateProgressBar("Mixing", mixingProgressBar, imageBadge3);
        updateProgressBar("Processing", processingProgressBar, imageBadge4);
        updateProgressBar("Production", productionProgressBar, imageBadge5);
        updateProgressBar("MusicTheory", musicTheoryProgressBar, imageBadge6);
    }

    private void generateAndUploadMockEntries() {
        // Create a list of 30 mock usernames
        // Call your repository method to upload the entry to Firestore
        userStatisticsRepository.generateMockValues();
    }

    private void generateUserMockValues() {
        userStatisticsRepository.generateUserMockValues();
    }

    private void deleteAllUserStatistics() {
        userStatisticsRepository.deleteAllUserStatistics();
    }
*/
//endregion

}

