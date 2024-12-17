package com.audioquiz.feature.quiz.presentation.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.audioquiz.feature.quiz.R;
import com.audioquiz.feature.quiz.presentation.viewmodel.QuestionViewModel;
import com.audioquiz.designsystem.extensions.FragmentExtension;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class QuizResultsFragment extends Fragment {
QuestionViewModel viewModel;
private Drawable goldMedal;
private Drawable silverMedal;
private Drawable bronzeMedal;
private Drawable incorrectMedal;

    @Inject
    public QuizResultsFragment() {
        // Default constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_quiz_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Declare ViewModel as a local variable
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);

        FragmentExtension fragmentExtension = new FragmentExtension();
        goldMedal = fragmentExtension.getDrawable(this, com.audioquiz.designsystem.R.drawable.ic_medal_gold2);
        silverMedal = fragmentExtension.getDrawable(this, com.audioquiz.designsystem.R.drawable.ic_medal_silver2);
        bronzeMedal = fragmentExtension.getDrawable(this, com.audioquiz.designsystem.R.drawable.ic_medal_bronze2);
        incorrectMedal = fragmentExtension.getDrawable(this, com.audioquiz.designsystem.R.drawable.ic_incorrect);

        // Get references to the ImageView and TextView
        ImageView imageAchievement = view.findViewById(R.id.image_achievement);
        TextView textAchievement = view.findViewById(R.id.text_achievement);

        // Observe userScore and update the quizResultsTextView and achievement image and text
        viewModel.getUserScore().observe(getViewLifecycleOwner(), score -> {
            ((TextView) view.findViewById(R.id.text_quiz_results)).setText(score);
           /*         if (score == 10) {
                        imageAchievement.setImageResource(R.drawable.ic_medal_gold);
                        textAchievement.setText("Perfect!");
                    } else if (score == 9) {
                        imageAchievement.setImageResource(R.drawable.ic_medal_silver);
                        textAchievement.setText("Very good!");
                    } else if (score == 8) {
                        imageAchievement.setImageResource(R.drawable.ic_medal_bronze);
                        textAchievement.setText("Nice!");
                    } else {
                        imageAchievement.setImageResource(R.drawable.ic_incorrect);
                        textAchievement.setText("Try again!");
                    }*/
        });

        viewModel.getQuizMedal().observe(getViewLifecycleOwner(), medal -> {
            switch (medal) {
                case "gold":
                    imageAchievement.setImageDrawable(goldMedal);
                    textAchievement.setText("Perfect!");
                    break;
                case "silver":
                    imageAchievement.setImageDrawable(silverMedal);
                    textAchievement.setText("Very good!");
                    break;
                case "bronze":
                    imageAchievement.setImageDrawable(bronzeMedal);
                    textAchievement.setText("Nice!");
                    break;
                default:
                    imageAchievement.setImageDrawable(incorrectMedal);
                    textAchievement.setText("Try again!");
                    break;
            }
        });

        view.findViewById(R.id.btn_finish_quiz).setOnClickListener(v -> {
           // Navigation.findNavController(v).navigate(R.id.action_quizResultsFragment_to_homeFragment);
            viewModel.resetIsQuizEnded();
        });

        Log.d("ViewModelInstance", "QuizResultsFragment ViewModel: " + viewModel);
    }    }