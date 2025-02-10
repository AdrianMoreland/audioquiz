package com.audioquiz.feature.home.view;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;

import com.audioquiz.feature.home.R;
import com.audioquiz.feature.home.databinding.BottomSheetCategoryBinding;
import com.audioquiz.feature.home.domain.CategoryViewContract;
import com.audioquiz.feature.home.domain.ChapterUi;
import com.audioquiz.feature.home.navigation.HomeCoordinatorEvent;
import com.audioquiz.feature.home.navigation.HomeFlowCoordinator;
import com.audioquiz.feature.home.presentation.viewmodel.BottomSheetCategoryViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class BottomSheetCategoryFragment extends BottomSheetDialogFragment {
    private static final String TAG = "BottomSheetCategory";
    private static final int TOTAL_CHAPTERS = 3; // Constant for progress calculation
    BottomSheetCategoryBinding binding;
    @Inject
    HomeFlowCoordinator homeFlowCoordinator;
    private BottomSheetCategoryViewModel viewModel;
    private Integer currentChapterOrdinal;
    private String category;
    private Button[] quizButtons;
    private TextView[] chapterTitles;
    private TextView[] chapterDescriptions;
    private LinearLayout[] chapterContainers;
    private List<ChapterUi.Chapter> chapters;
    private int primaryColor, inversePrimaryColor, surfaceColor, onTertiaryColor, onSurfaceColor;
    private OnQuizNavigateListener onQuizNavigateListener;

    // Setter for the listener
    public void setOnQuizNavigateListener(OnQuizNavigateListener listener) {
        this.onQuizNavigateListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), com.audioquiz.designsystem.R.style.AppTheme_BottomSheetDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();

        FrameLayout bottomSheet = Objects.requireNonNull(dialog).findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            behavior.setPeekHeight(BottomSheetBehavior.STATE_EXPANDED);

            // Find the separator view
            View separator = bottomSheet.findViewById(R.id.container_arrow);
            if (separator != null) {
                // Wait for the layout pass to get the height of the separator
                separator.post(() -> {
                    // Set the initial height of the bottom sheet to the height of the separator
                    int height = separator.getBottom();
                    behavior.setPeekHeight(height);
                });
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = BottomSheetCategoryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(BottomSheetCategoryViewModel.class);
        binding.setViewModel(viewModel); // Set the ViewModel in the binding


        setupColors();
        initializeViews();

        Bundle arguments = getArguments();
        if (arguments != null){
            category = arguments.getString("category");
            currentChapterOrdinal = arguments.getInt("current_chapter");
        } else {
            handleError("Missing arguments");
        }
        if (category == null) handleError("Missing category argument");
        binding.setCategory(category);
        binding.tvCategory.setText(category);

        setupButtonListeners();

        viewModel.loadCategoryData(category);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Observe the state and events from the ViewModel.
        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.viewState().observe(getViewLifecycleOwner(), this::onViewStateChanged);
        Timber.tag(TAG).d("observeViewModel called");
    }

    /**
     * When the state changes, update all the text views with the corresponding strings.
     */
    private void onViewStateChanged(CategoryViewContract.State state) {
        if (state != null) {
            Timber.tag(TAG).d("onViewStateChanged called");
            // Update category header if needed.
            binding.tvCategory.setText(state.getCategoryName());
            // Update each chapter’s title and description.
            binding.tvNameChapter1.setText(state.getNameChapter1());
            binding.tvDescriptionChapter1.setText(state.getDescriptionChapter1());

            binding.tvNameChapter2.setText(state.getNameChapter2());
            binding.tvDescriptionChapter2.setText(state.getDescriptionChapter2());

            binding.tvNameChapter3.setText(state.getNameChapter3());
            binding.tvDescriptionChapter3.setText(state.getDescriptionChapter3());

            // Update progress bar based on the current chapter (from state)
            float progress = ((float) currentChapterOrdinal / TOTAL_CHAPTERS) * 100;
            binding.progressCategory.setProgress((int) progress);

            // Apply styling to chapters based on the current chapter
            if (chapters != null && currentChapterOrdinal > 0 && currentChapterOrdinal <= chapters.size()) {
                ChapterUi.Chapter currentChapter = chapters.get(currentChapterOrdinal - 1);
                applyChapterStyles(currentChapter);
            }
        }
    }

    private void initializeViews() {
        chapterTitles = new TextView[]{
                binding.tvNameChapter1,
                binding.tvNameChapter2,
                binding.tvNameChapter3,
                binding.tvTest,
                binding.tvCategoryEndTitle
        };
        chapterDescriptions = new TextView[]{
                binding.tvDescriptionChapter1,
                binding.tvDescriptionChapter2,
                binding.tvDescriptionChapter3,
                binding.tvTestDescription,
                binding.tvCategoryEndDescription
        };
        chapterContainers = new LinearLayout[]{
                binding.containerChapter1,
                binding.containerChapter2,
                binding.containerChapter3,
                binding.containerTest,
                binding.containerCategoryEnd
        };
        quizButtons = new Button[]{
                binding.btnChapter1,
                binding.btnChapter2,
                binding.btnChapter3,
                binding.btnTest,
                binding.btnFinishQuiz
        };
        chapters = new ArrayList<>();
        chapters.add(new ChapterUi.Chapter(0, binding.tvNameChapter1, binding.tvDescriptionChapter1, binding.containerChapter1, binding.btnChapter1));
        chapters.add(new ChapterUi.Chapter(1, binding.tvNameChapter2, binding.tvDescriptionChapter2, binding.containerChapter2, binding.btnChapter2));
        chapters.add(new ChapterUi.Chapter(2, binding.tvNameChapter3, binding.tvDescriptionChapter3, binding.containerChapter3, binding.btnChapter3));
        chapters.add(new ChapterUi.Chapter(3, binding.tvTest, binding.tvTestDescription, binding.containerTest, binding.btnTest));
        chapters.add(new ChapterUi.Chapter(4, binding.tvCategoryEndTitle, binding.tvCategoryEndDescription, binding.containerCategoryEnd, binding.btnFinishQuiz));
    }

    private void applyChapterStyles(ChapterUi.Chapter currentChapter) {
        final float scale = getResources().getDisplayMetrics().density;
        int widthLarge = (int) (44 * scale + 0.5f);
        int heightLarge = (int) (44 * scale + 0.5f);
        int widthSmall = (int) (25 * scale + 0.5f);
        int heightSmall = (int) (25 * scale + 0.5f);

        for (int i = 0; i <= currentChapter.index + 2 && i < chapters.size(); i++) {
            if (i == currentChapter.index) {
                styleChapter(i, ChapterStatus.CURRENT, widthLarge, heightLarge);
            } else if (i < currentChapter.index) {
                styleChapter(i, ChapterStatus.COMPLETED, widthSmall, heightSmall);
            } else {
                styleChapter(i, ChapterStatus.FUTURE, 0, 0); // No size changes for future chapters
            }
        }
    }

    private void styleChapter(int index, ChapterStatus status, int width, int height) {
        TextView title = chapterTitles[index];
        TextView description = chapterDescriptions[index];
        LinearLayout container = chapterContainers[index];
        Button button = quizButtons[index];

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) button.getLayoutParams();
        params.width = width;
        params.height = height;
        button.setLayoutParams(params);

        switch (status) {
            case CURRENT:
                title.setTextColor(inversePrimaryColor);
                description.setTextColor(onTertiaryColor);
                description.setVisibility(View.VISIBLE);
                container.setBackgroundResource(com.audioquiz.designsystem.R.drawable.shape_frame_rounded_chapter);
                button.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
                button.setText("Start");
                break;
            case COMPLETED:
                Drawable drawable = ContextCompat.getDrawable(requireContext(), com.audioquiz.designsystem.R.drawable.ic_correct);
                DrawableCompat.setTint(DrawableCompat.wrap(Objects.requireNonNull(drawable)), ContextCompat.getColor(requireContext(), com.audioquiz.designsystem.R.color.md_theme_surface));
                button.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                break;
            case FUTURE:
                title.setTextColor(onSurfaceColor);
                container.setBackgroundResource(0);
                break;
        }
    }

    private void setupButtonListeners() {
        for (int i = 0; i < quizButtons.length; i++) {
            final int chapter = i + 1;
            quizButtons[i].setOnClickListener(v -> {
                if (onQuizNavigateListener == null) return;
                onQuizNavigateListener.onQuizNavigate(chapter);
                dismiss();  // Dismiss when the button is clicked.
            });
        }
    }

    private void handleError(String message) {
        Timber.tag(TAG).e(message);
        // You can add more error handling logic here, like showing a Toast or Snackbar
        dismiss(); // Dismiss the fragment
    }

    private int getColor(@ColorRes int colorResId) {
        return ContextCompat.getColor(requireContext(), colorResId);
    }

    private void setupColors() {
        primaryColor = getColor(com.audioquiz.designsystem.R.color.md_theme_primary);
        inversePrimaryColor = getColor(com.audioquiz.designsystem.R.color.md_theme_inversePrimary);
        surfaceColor = getColor(com.audioquiz.designsystem.R.color.md_theme_surface);
        onTertiaryColor = getColor(com.audioquiz.designsystem.R.color.md_theme_onTertiaryContainer);
        onSurfaceColor = getColor(com.audioquiz.designsystem.R.color.md_theme_onSurface);
    }

    private enum ChapterStatus {
        CURRENT, COMPLETED, FUTURE
    }

    // Define the callback interface
    public interface OnQuizNavigateListener {
        void onQuizNavigate(int chapter);
    }
}