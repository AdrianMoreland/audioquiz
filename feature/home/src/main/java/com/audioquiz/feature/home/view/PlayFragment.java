package com.audioquiz.feature.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.audioquiz.feature.home.R;
import com.google.android.material.button.MaterialButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PlayFragment extends Fragment {
//QuestionViewModel questionViewModel;

    MaterialButton pitchButton;
    MaterialButton intervalsButton;
    MaterialButton allQuestionsButton;
    CardView pitchCard;
    CardView intervalsCard;
    CardView allQuestionsCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

    }

/*    @Override
    protected int getNavGraphActionId(String fragmentName, int buttonId) {
        if (fragmentName.equals("playFragment")) {
            if (buttonId == R.id.btn_settings) {
                return R.id.action_playFragment_to_settingsFragment;
            } else if (buttonId == R.id.btn_stats) {
                return R.id.action_playFragment_to_userStatisticsFragment;
            } else if (buttonId == R.id.btn_rank) {
                return R.id.action_playFragment_to_rankFragment;
            } else if (buttonId == R.id.btn_learn) {
                return R.id.action_playFragment_to_homeFragment;
            } else if (buttonId == R.id.btn_play) {
                return 0;
            } else {
                throw new IllegalArgumentException("Invalid button ID");
            }
        } else {
            throw new IllegalArgumentException("Invalid fragment name");
        }
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        pitchCard = view.findViewById(R.id.card7);
        pitchCard.setOnClickListener(v -> navigateToQuiz("pitch", 1));
        intervalsCard = view.findViewById(R.id.card8);
        intervalsCard.setOnClickListener(v -> openBottomSheet());
        allQuestionsCard = view.findViewById(R.id.card9);
        allQuestionsCard.setOnClickListener(v -> navigateToQuiz("all", 0));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  setupNavigationButtons(view, "playFragment");

        //TODO setup blurview in design system module
//        BlurView blurView = view.findViewById(R.id.blurView);
//        BlurViewUtils.setupBlurView(requireActivity(), blurView, 10f);
    }

/*
    private void setupNavigationButtons(View view) {
        View bottomNavbar = view.findViewById(R.id.bottom_navbar_frame);
        // Set up the navigation buttons
        MaterialButton imageButton = view.findViewById(R.id.btn_settings);
        imageButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_settingsFragment));
        setupNavigationButton(view, R.id.btn_settings, R.id.action_playFragment_to_settingsFragment);
        setupNavigationButton(bottomNavbar, R.id.btn_stats, R.id.action_playFragment_to_userStatisticsFragment);
        setupNavigationButton(bottomNavbar, R.id.btn_rank, R.id.action_playFragment_to_rankFragment);
        setupNavigationButton(bottomNavbar, R.id.btn_learn, R.id.action_playFragment_to_homeFragment);
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
*/
    private void openBottomSheet() {
        BottomSheetIntervalsFragment bottomSheetFragment = new BottomSheetIntervalsFragment();
        bottomSheetFragment.show(getChildFragmentManager(), bottomSheetFragment.getTag());
    }


    private void navigateToQuiz(String category, int chapter) {
        String quizType = "play";
     //   questionViewModel.startQuiz(category, chapter, quizType);
        Bundle args = new Bundle();
        args.putString("category", category);
        args.putInt("level", chapter); // Add this line
     //   NavHostFragment.findNavController(this).navigate(R.id.action_playFragment_to_questionFragment, args);
    }


}