package com.audioquiz.feature.home.view;

import android.os.Bundle;
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
public class HomeFragment extends Fragment implements BottomSheetCategoryFragment.OnQuizNavigateListener {
    private static final String TAG = "HomeFragment";
    private final CompositeDisposable disposables = new CompositeDisposable();
    @Inject
    HomeFlowCoordinator homeFlowCoordinator;
    private HomeViewModel homeViewModel;
    private CategoryAdapter adapter;
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private String currentCategory;

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
                homeViewModel.process(new HomeViewContract.Event.OnCategoryCardClicked(
                        category.getName(), category.getCurrentChapter())));
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
        if (state == null) return;
        binding.frameIconsCompleted.setVisibility(state.isShowBadges() ? View.VISIBLE : View.GONE);
        Timber.tag(TAG).d("onViewStateChanged called");
    }

    private void onViewEffectReceived(HomeViewContract.Effect effect) {
        if (effect == null) return;
        if (effect instanceof HomeViewContract.Effect.ShowCategoryBottomSheet bottomSheetEffect) {
            showCategoryBottomSheet(bottomSheetEffect.getCategory(), bottomSheetEffect.getCurrentChapter());
        } else if (effect instanceof HomeViewContract.Effect.ShowErrorToast errorToastEffect) {
            Toast.makeText(getContext(), errorToastEffect.getError(), Toast.LENGTH_SHORT).show();
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
        Timber.tag(TAG).d("Showing bottom sheet for %s, chapter: %d", category, currentChapter);
        if (currentChapter == 0) {
            currentChapter = 1;
        }

        currentCategory = category; // Store category for later use

        BottomSheetCategoryFragment bottomSheetFragment = new BottomSheetCategoryFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putInt("current_chapter", currentChapter);
        bottomSheetFragment.setArguments(args);

        bottomSheetFragment.setOnQuizNavigateListener(this);
        bottomSheetFragment.show(getChildFragmentManager(), "BottomSheetCategoryFragment");
    }

    @Override
    public void onQuizNavigate(int chapter) {
        if (currentCategory == null) {
            Timber.tag(TAG).e("Category is null, cannot start quiz.");
            return;
        }
        Timber.tag(TAG).d("onQuizNavigate called with chapter: %d", chapter);

        Bundle args = new Bundle();
        args.putString("category", currentCategory);
        args.putInt("current_chapter", chapter);

        homeViewModel.process(new HomeViewContract.Event.OnStartQuizTriggered(args));

        Timber.tag(TAG).d("Navigating to quiz: Category=%s, Chapter=%d", currentCategory, chapter);
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
}

