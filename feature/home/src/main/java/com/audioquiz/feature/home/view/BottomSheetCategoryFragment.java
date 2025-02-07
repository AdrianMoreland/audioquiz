package com.audioquiz.feature.home.view;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.audioquiz.feature.home.domain.HomeViewContract;
import com.audioquiz.feature.home.presentation.viewmodel.BottomSheetCategoryViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class BottomSheetCategoryFragment extends BottomSheetDialogFragment {
    private BottomSheetCategoryViewModel bottomSheetCategoryViewModel;
    BottomSheetCategoryBinding binding;
    private Button[] quizButtons;
    private TextView[] chapterTitles;
    private TextView[] chapterDescriptions;
    private LinearLayout[] chapterContainers;
    private List<HomeViewContract.Chapter> chapters;

    private int primaryColor, inversePrimaryColor, surfaceColor, onTertiaryColor, onSurfaceColor;


    private static final int TOTAL_CHAPTERS = 3; // Constant for progress calculation

    private static final Map<String, HomeViewContract.ChapterResources> RESOURCES = new HashMap<>();
    static {
        RESOURCES.put("soundwaves", new HomeViewContract.ChapterResources(com.audioquiz.designsystem.R.array.soundwaves_chapters, com.audioquiz.designsystem.R.array.soundwaves_descriptions));
        RESOURCES.put("synthesis", new HomeViewContract.ChapterResources(com.audioquiz.designsystem.R.array.synthesis_chapters, com.audioquiz.designsystem.R.array.synthesis_descriptions));
        RESOURCES.put("production", new HomeViewContract.ChapterResources(com.audioquiz.designsystem.R.array.production_chapters, com.audioquiz.designsystem.R.array.production_descriptions));
        RESOURCES.put("mixing", new HomeViewContract.ChapterResources(com.audioquiz.designsystem.R.array.mixing_chapters, com.audioquiz.designsystem.R.array.mixing_descriptions));
        RESOURCES.put("processing", new HomeViewContract.ChapterResources(com.audioquiz.designsystem.R.array.processing_chapters, com.audioquiz.designsystem.R.array.processing_descriptions));
        RESOURCES.put("musictheory", new HomeViewContract.ChapterResources(com.audioquiz.designsystem.R.array.musictheory_chapters, com.audioquiz.designsystem.R.array.musictheory_descriptions));
    }

    private static final String ARG_CATEGORY = "category";
    private static final String ARG_CURRENT_CHAPTER = "current_chapter";

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
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

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
        bottomSheetCategoryViewModel = new ViewModelProvider(this).get(BottomSheetCategoryViewModel.class);
        binding.setViewModel(bottomSheetCategoryViewModel); // Set the ViewModel in the binding
        View view = binding.getRoot();
        setupColors();


        Bundle arguments = getArguments();
        if (arguments != null) {
            String category = arguments.getString(ARG_CATEGORY);
            if (category != null) {
                binding.setCategory(category);
                int currentChapterOrdinal = arguments.getInt(ARG_CURRENT_CHAPTER);
                Timber.tag("BottomSheetCategory").d("Current chapter ordinal: %d", currentChapterOrdinal);
                initializeViews();
                setupViews(category, currentChapterOrdinal);
            } else {
                handleError("Missing category argument");
            }
        } else {
            handleError("Missing arguments");
        }

        return view;
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

    private void setupViews(String category, int currentChapterOrdinal) {
        HomeViewContract.Chapter chapter = chapters.get(currentChapterOrdinal - 1);
     //   initializeViews();

        binding.setCategory(category);
        Timber.tag("BottomSheetCategory").d("Category: %s", category);

        binding.tvCategory.setText(category);

        final float progress = ((float) (currentChapterOrdinal) / TOTAL_CHAPTERS) * 100;
        binding.progressCategory.setProgress((int) progress);

        setupChapterData(category);
        applyChapterStyles(chapter);
        setupButtonListeners(category);
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
        chapters.add(new HomeViewContract.Chapter(0, binding.tvNameChapter1, binding.tvDescriptionChapter1, binding.containerChapter1, binding.btnChapter1));
        chapters.add(new HomeViewContract.Chapter(1, binding.tvNameChapter2, binding.tvDescriptionChapter2, binding.containerChapter2, binding.btnChapter2));
        chapters.add(new HomeViewContract.Chapter(2, binding.tvNameChapter3, binding.tvDescriptionChapter3, binding.containerChapter3, binding.btnChapter3));
        chapters.add(new HomeViewContract.Chapter(3, binding.tvTest, binding.tvTestDescription, binding.containerTest, binding.btnTest));
        chapters.add(new HomeViewContract.Chapter(4, binding.tvCategoryEndTitle, binding.tvCategoryEndDescription, binding.containerCategoryEnd, binding.btnFinishQuiz));
    }

    private void setupChapterData(String category) {
        HomeViewContract.ChapterResources resources = RESOURCES.getOrDefault(category.toLowerCase(), new HomeViewContract.ChapterResources(0, 0));
        int chapterArrayResId = Objects.requireNonNull(resources).chaptersResId;
        int descriptionsArrayResId = resources.descriptionsResId;
        if (chapterArrayResId != 0) {
            String[] chapters = getResources().getStringArray(chapterArrayResId);
            for (int i = 0; i < chapters.length && i < chapterTitles.length; i++) {
                chapterTitles[i].setText(chapters[i]);
            }
            binding.tvTest.setText(R.string.final_exam);
            binding.tvCategoryEndTitle.setText(R.string.done);
        }

        if (descriptionsArrayResId != 0) {
            String[] descriptions = getResources().getStringArray(descriptionsArrayResId);
            for (int i = 0; i < descriptions.length && i < chapterDescriptions.length; i++) {
                chapterDescriptions[i].setText(descriptions[i]);
            }
        }
    }

    private void applyChapterStyles(HomeViewContract.Chapter currentChapter) {
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

    private enum ChapterStatus {
        CURRENT, COMPLETED, FUTURE;
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
            case FUTURE:title.setTextColor(onSurfaceColor);
                container.setBackgroundResource(0);
                break;
        }
    }

    private void setupButtonListeners(String category) {
        for (int chapterIndex = 0; chapterIndex < quizButtons.length; chapterIndex++) {
            final int chapter = chapterIndex + 1;
            quizButtons[chapterIndex].setOnClickListener(v -> navigateToQuiz(category, chapter));
        }
    }

    private void navigateToQuiz(String category, int chapter) {
        String quizType = "lesson";
      //  homeViewModel.startQuiz(category, chapter, quizType);
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putInt("level", chapter);
     //   NavHostFragment.findNavController(this).navigate(com.example.ui.R.id.action_global_infoBottomSheetCategory_to_questionFragment);        dismiss();
    }

    private void handleError(String message) {
        Timber.tag("BottomSheetCategoryFragment").e(message);
        // You can add more error handling logic here, like showing a Toast or Snackbar
        dismiss(); // Dismiss the fragment
    }

}
