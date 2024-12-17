package com.audioquiz.feature.quiz.presentation.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.audioquiz.core.domain.service.FrequencyPlayer;
import com.audioquiz.feature.quiz.R;
import com.audioquiz.feature.quiz.databinding.BottomSheetDialogBinding;
import com.audioquiz.feature.quiz.databinding.FragmentQuestionBinding;
import com.audioquiz.feature.quiz.model.QuestionUi;
import com.audioquiz.feature.quiz.presentation.viewmodel.QuestionViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import me.grantland.widget.AutofitHelper;
import me.grantland.widget.AutofitTextView;

@AndroidEntryPoint
public class QuestionFragment extends Fragment {
    private static final String TAG = "QuestionFragment";
    private static final String CATEGORY_KEY = "category";
    private final List<Button> allButtons = new ArrayList<>();
    private final List<Button> group1Buttons = new ArrayList<>();
    private final List<Button> group2Buttons = new ArrayList<>();
    FrequencyPlayer frequencyPlayer;
    int[] regularButtonsIdArray;
    int[] intervalButtonsIdArray;
    Integer maxQuestions = 7;
    boolean isButtonDisabled = false;
    private QuestionViewModel viewModel;
    private int layoutId;
    private View newLayout = null;
    private QuestionUi currentQuestion;
    private String category;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private String type;
    private int selectedButtonColor;
    private int originalButtonColor;
    private int incompatibleButtonColor;
    private int colorCorrect;
    private int colorIncorrect;
    private int submitEnabledShape;
    private int submitDisabledShape;
    private int transparentBottomSheetExplanation;
    private int iconCorrect;
    private int iconIncorrect;
    private int progress = 0;
    private Integer questionCount;
    private boolean isAnswerSubmitted;
    private boolean isCorrect;
    private boolean isLastQuestion;
    private int selectedOptionIndex = -1;
    private int selectedOptionIndex2 = -1;
    private int correctOptionIndex1 = -1;
    private int correctOptionIndex2 = -1;
    private List<Integer> incompatibleButtons = new ArrayList<>();
    private FragmentQuestionBinding binding; // View binding

    /*private Button submitButton;
    private Button playButton;
    private TextView questionTextView;
    private TextView livesTextView;
    private LinearProgressIndicator progressIndicator;*/

    @Inject
    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Create a callback to handle the back button press
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            /** @noinspection deprecation*/
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                new AlertDialog.Builder(requireContext())
                        .setTitle("Are you sure?")
                        .setMessage("The current quiz progress will be lost.")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Disable callback and allow the back press to be handled by the system or any other enabled callbacks
                            setEnabled(false);
                            // Perform the back navigation action
                            requireActivity().onBackPressed();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");
        // Retrieve the category from arguments
        category = requireArguments().getString(CATEGORY_KEY, "default");
        // Inflate the fragment layout using view binding
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Call the superclass method
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated called");
        TypedArray intervalButtonsIdArrayResource = getResources().obtainTypedArray(R.array.intervalButtonsIdArray);
        intervalButtonsIdArray = new int[intervalButtonsIdArrayResource.length()];
        for (int i = 0; i < intervalButtonsIdArray.length; i++) {
            intervalButtonsIdArray[i] = intervalButtonsIdArrayResource.getResourceId(i, -1);
        }
        TypedArray regularButtonsIdArrayResource = getResources().obtainTypedArray(R.array.regularButtonsIdArray);
        regularButtonsIdArray = new int[regularButtonsIdArrayResource.length()];
        for (int i = 0; i < regularButtonsIdArray.length; i++) {
            regularButtonsIdArray[i] = regularButtonsIdArrayResource.getResourceId(i, -1);
        }
        intervalButtonsIdArrayResource.recycle();
        regularButtonsIdArrayResource.recycle();

        setupViews();
        observeLiveData();

        selectedButtonColor = com.audioquiz.designsystem.R.color.md_theme_primaryFixedDim;
        originalButtonColor = com.audioquiz.designsystem.R.color.md_theme_primaryFixedDim;
        incompatibleButtonColor = com.audioquiz.designsystem.R.color.md_theme_surfaceContainer;
        colorCorrect = com.audioquiz.designsystem.R.color.colorCorrect;
        colorIncorrect = com.audioquiz.designsystem.R.color.colorIncorrect;
        submitEnabledShape = com.audioquiz.designsystem.R.drawable.shape_button_submit;
        submitDisabledShape = com.audioquiz.designsystem.R.drawable.shape_button_submit_disabled;
        iconCorrect = com.audioquiz.designsystem.R.drawable.ic_correct;
        iconIncorrect = com.audioquiz.designsystem.R.drawable.ic_incorrect;
        transparentBottomSheetExplanation = com.audioquiz.designsystem.R.style.TransparentBottomSheetExplanation;
        // Load a specific question from the ViewModel
        viewModel.loadNextQuestion();

/*        if (getActivity() != null) {
            selectedButtonColor = getResources().getColor(R.color.md_theme_primaryFixedDim, getActivity().getTheme());
            originalButtonColor = getResources().getColor(R.color.md_theme_primaryFixed, getActivity().getTheme());
        }*/
    }

    // OBSERVE_LIVE_DATA_FROM_VIEW_MODEL
    private void observeLiveData() {
        viewModel.getMaxQuestionsLiveData().observe(getViewLifecycleOwner(), maxQuestionsLiveData -> maxQuestions = maxQuestionsLiveData);
        viewModel.getIsLastQuestion().observe(getViewLifecycleOwner(), isLastQuestionLiveData -> {
            Log.d(TAG, "isLastQuestion: " + isLastQuestionLiveData);
            isLastQuestion = isLastQuestionLiveData;
        });
        viewModel.getQuestionCountLiveData().observe(getViewLifecycleOwner(), questionCountLiveData -> {
            Log.d(TAG, "questionCount: " + questionCountLiveData);
            questionCount = questionCountLiveData;
        });
        viewModel.getQuestionLiveData().observe(getViewLifecycleOwner(), question -> {
            currentQuestion = question;
            onQuestionLoaded(question);
        });
        viewModel.getIsCorrectLiveData().observe(getViewLifecycleOwner(), isCorrectLiveData -> isCorrect = isCorrectLiveData);
        viewModel.getNumberOfLives().observe(getViewLifecycleOwner(), lives -> binding.textLives.setText(lives));
        viewModel.getCorrectOption1().observe(getViewLifecycleOwner(), correctOption1 -> {
            if (correctOption1 != null) {
                correctOptionIndex1 = correctOption1;
                highlightCorrectButtons();
            }
        });
        viewModel.getCorrectOption2().observe(getViewLifecycleOwner(), correctOption2 -> {
            if (correctOption2 != null) {
                Log.d("observeLiveData", "Correct option 2 updated: " + correctOption2);
                correctOptionIndex2 = correctOption2;
                highlightCorrectButtons();
            }
        });
    }

    private void setupViews() {
        binding.submitButton.setOnClickListener(v -> submitAnswer());

        if ("pitch".equals(category) || "intervals".equals(category)) {
            binding.questionTextView.setVisibility(View.GONE);
            binding.containerPlayButton.setVisibility(View.VISIBLE);
            binding.containerSpeaker.setVisibility(View.VISIBLE);
        } else {
            binding.questionTextView.setVisibility(View.VISIBLE);
            binding.containerPlayButton.setVisibility(View.GONE);
            binding.containerSpeaker.setVisibility(View.GONE);
        }
    }

    // QUESTION_LOADING_AND_LAYOUT
    private void onQuestionLoaded(QuestionUi question) {
        if (question != null) {
            if ("pitch".equals(category) || "intervals".equals(category)) {
                setupPlayButton(question);
            } else {
                // Set question text
                binding.questionTextView.setText(question.getQuestionText());
            }
            // Adjust layout based on question type and options
            adjustLayout(question);
            // Reset the color of the submit button
            updateSubmitButtonColor(false);
        } else {
            Log.e(TAG, "QuestionDto is null. Cannot load question.");
        }
    }

    // SETUP_PLAY_BUTTON
    private void setupPlayButton(QuestionUi question) {
        binding.buttonPlaySound.setOnClickListener(v -> viewModel.playFrequency(question));
    }

    // ADJUST_LAYOUT_BASED_ON_QUESTION_TYPE
    private void adjustLayout(QuestionUi question) {
        type = question.getType();
        optionOne = question.getOption1();
        optionTwo = question.getOption2();
        optionThree = question.getOption3();
        optionFour = question.getOption4();

        FrameLayout answerContainer = binding.answerContainer;
        answerContainer.removeAllViews();


        layoutId = getLayoutId();

        Context context = getContext();
        if (context != null) {
            // Inflate the new layout
            newLayout = LayoutInflater.from(context).inflate(layoutId, answerContainer, false);
            Objects.requireNonNull(answerContainer).addView(newLayout);
            answerContainer.setVisibility(View.VISIBLE);

            if (category.equals("intervals")) {
                setupIntervalButtons();
            } else {
                setupOptionsButtons();
            }
        } else {
            Log.d(TAG, "adjustLayout: Context is null");
        }
    }

    // LAYOUT_SELECTION
    private int getLayoutId() {
        if (type != null && type.equals("yes_no")) {
            layoutId = R.layout.answer_yes_no_layout;
        } else if (category != null && category.equals("intervals")) {
            layoutId = R.layout.answer_intervals;
        } else if (optionOne.length() > 20 || optionTwo.length() > 20 || optionThree.length() > 20 || optionFour.length() > 20) {
            layoutId = R.layout.answer_linear_layout;
        } else {
            layoutId = R.layout.answer_grid_layout;
        }
        return layoutId;
    }

    // SUBMIT_BUTTON_COLOR_UPDATE
    private void updateSubmitButtonColor(boolean isSelected) {
        if (getActivity() != null) {
            if (isSelected) {
                // If an option is selected, set the background to the drawable
                binding.submitButton.setBackgroundResource(submitEnabledShape);
            } else {
                // If no option is selected, set the color to disabled
                binding.submitButton.setBackgroundResource(submitDisabledShape);
            }
        }
    }

    // ----------------- OPTION_BUTTONS -----------------
    // BUTTONS_SETUP
    private void setupButtons(int[] buttonIds, String[] options) {
        // Reset button states before setting up new options
        for (int buttonId : buttonIds) {
            Button button = binding.getRoot().findViewById(buttonId);
            if (button != null) {
                button.setBackgroundColor(originalButtonColor); // Reset to default color
                button.setEnabled(true); // Enable button for new question
                button.setOnClickListener(null); // Clear previous click listeners
            }
        }
        for (int i = 0; i < buttonIds.length; i++) {
//            Log.d("setupButtons", "Button ID: " + buttonIds[i] + ", Option: " + options[i]);
            initializeButtons(buttonIds[i], i, options[i]);
        }
    }

    private void setupOptionsButtons() {
        setupButtons(regularButtonsIdArray,
                new String[]{optionOne, optionTwo, optionThree, optionFour});
    }

    private void setupIntervalButtons() {
        setupButtons(intervalButtonsIdArray,
                getResources().getStringArray(com.audioquiz.designsystem.R.array.intervalButtonsNamesArray));
    }

    // BUTTON_INITIALIZATION
    private void initializeButtons(int buttonID, int index, @Nullable String optionText) {
        MaterialButton button = newLayout.findViewById(buttonID);
        if (button == null) return;
        // Set the button name
        button.setTag(getResources().getResourceEntryName(buttonID));

        if (!category.equals("intervals")) {
            button.setText(optionText);
        }
        allButtons.add(button);
        button.setOnClickListener(v -> handleButtonClick(button, index));
    }

    // BUTTON_CLICK_HANDLING
    private void handleButtonClick(MaterialButton button, int index) {
        Log.d("handleButtonClick", "Button clicked: " + button.getTag() + " at index: " + index);

        if (index == -1) return;
        int oldOptionIndex;
        List<Button> groupButtons;
        if (index <= 7) {
            oldOptionIndex = selectedOptionIndex;
            selectedOptionIndex = index;
            groupButtons = group1Buttons;
            setOptionButtonsEnabled(true, group2Buttons);
            selectedOptionIndex2 = -1;
        } else { // This button belongs to the second group
            oldOptionIndex = selectedOptionIndex2;
            selectedOptionIndex2 = index;
            groupButtons = group2Buttons;
        }
        handleButtonSelection(groupButtons, button, index, oldOptionIndex);

        if (!category.equals("intervals") || (selectedOptionIndex != -1 && selectedOptionIndex2 != -1)) {
            updateSubmitButtonColor(true);
        }
        // Disable incompatible buttons based on the selected interval quality
        if (index <= 7 && category.equals("intervals")) {
            toggleIncompatibleButtons(index);
        }
    }

    private void handleButtonSelection(List<Button> groupButtons, MaterialButton button, int index, int selectedIndex) {
        if (selectedIndex != index) {
            groupButtons.add(button);
            setOptionButtonsEnabled(true, groupButtons);
            button.setBackgroundColor(selectedButtonColor);
            button.setSelected(true);
        }
    }

    private void toggleIncompatibleButtons(int index) {
        incompatibleButtons.clear();
        incompatibleButtons = viewModel.disableIncompatibleButtons(index);

        for (int i = 8; i <= 12; i++) {
            Button button = getSelectedButton(i);
            if (button != null) {
                if (incompatibleButtons.contains(i)) {
                    button.setEnabled(false);
                    button.setBackgroundColor(incompatibleButtonColor);
                    isButtonDisabled = true;
                } else {
                    button.setEnabled(true);
                    button.setBackgroundColor(originalButtonColor);
                    isButtonDisabled = false;
                }
            }
        }
    }

    private Button getSelectedButton(int index) {
        if (index >= 0 && index < allButtons.size()) {
            //   Log.d("getSelectedButton", "Selected button: " + allButtons.get(index).getTag() + " at index: " + index);
            return allButtons.get(index);
        } else {
            Log.e("getSelectedButton", "Selected button is null" + index);
            return null;
        }
    }

    private void setOptionButtonsEnabled(boolean enabled, List<Button> groupButtons) {
        int color = enabled ? originalButtonColor : incompatibleButtonColor;
        for (Button button : groupButtons) {
            if (button != null) {
                button.setEnabled(enabled);
                button.setSelected(!enabled);
                if (!isAnswerSubmitted) {
                    button.setBackgroundColor(color);  // Reset option buttons
                }
            }
        }
        updateSubmitButtonColor(!enabled);   // Reset submit button
    }

    public void highlightCorrectButtons() {
        // Update the UI based on correctness
        setButtonColor(selectedOptionIndex, correctOptionIndex1, isCorrect);
        if (selectedOptionIndex2 != -1) {
            setButtonColor(selectedOptionIndex2, correctOptionIndex2, isCorrect);
        }
    }

    private void setButtonColor(int selectedOption, int correctOption, boolean isCorrect) {
        Log.d("setButtonColor", "Selected button: " + selectedOption + " Correct button: " + correctOption + " Is correct: " + isCorrect);
        if (selectedOption != -1 && correctOption != -1) {
            int color = isCorrect ? colorCorrect : colorIncorrect;
            Button selectedButton = getSelectedButton(selectedOption);
            Button correctButton = getSelectedButton(correctOption);
            if (selectedButton != null) {
                selectedButton.setBackgroundColor(color);
            }
            if (selectedOption != correctOption && correctButton != null) {
                correctButton.setBackgroundColor(colorCorrect);
            }
        }
    }

    // ANSWER_SUBMISSION_AND_NAVIGATION
    public void submitAnswer() {
        isAnswerSubmitted = true; // Set isAnswerSubmitted to true
        // Disable the submit button
        binding.submitButton.setEnabled(false);

        if (selectedOptionIndex != -1) {
            updateProgress(); // Update the progress
            // Submit the answer through the ViewModel
            viewModel.submitUserAnswer(selectedOptionIndex, selectedOptionIndex2);
            // Update the UI based on correctness
            highlightCorrectButtons();
            // Show the dialog with the appropriate message
            showNextQuestionDialog(Boolean.TRUE.equals(isCorrect) ? "Correct!" : "That's not right...", isCorrect);
            setOptionButtonsEnabled(false, allButtons); // Disable option buttons
        } else {
            // Display a Toast message
            Toast.makeText(getContext(), "No option selected!", Toast.LENGTH_SHORT).show();
            // Re-enable the submit button
            binding.submitButton.setEnabled(true);
            isAnswerSubmitted = false; // Set isAnswerSubmitted back to false
        }
    }

    private void updateProgress() {
        if (maxQuestions != null) {
            LinearProgressIndicator progressIndicator = binding.quizProgressIndicator;
            int progressIncrement = 100 / maxQuestions;
            // Update progress
            progress += progressIncrement;
            Log.d(TAG, "Progress: " + progress + ", max questions: " + maxQuestions);
            progressIndicator.setProgressCompat(progress, true);
            progressIndicator.setProgressCompat(progress, true);
        } else {
            Log.e(TAG, "maxQuestions is null. Cannot update progress.");
        }
    }


    // NEXT_QUESTION_DIALOG
    public void showNextQuestionDialog(String message, boolean isCorrect) {
        Context context = getContext();
        if (context == null || getView() == null) {
            Log.e(TAG, "Context is null or view is not created. Cannot show the dialog.");
            return;
        }

        // Inflate the binding object
        BottomSheetDialogBinding binding = BottomSheetDialogBinding.inflate(LayoutInflater.from(context));

        // Create a BottomSheetDialog with transparent style
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context, com.audioquiz.designsystem.R.style.TransparentBottomSheetExplanation);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        // Set the content view using binding's root
        bottomSheetDialog.setContentView(binding.getRoot());

        // Get the BottomSheetBehavior from the BottomSheetDialog
        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) binding.getRoot().getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        // Remove the dim effect
        Window window = bottomSheetDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0; // set dim amount to 0
            window.setAttributes(windowParams);
        }

        // Access views using binding
        binding.tvMessage.setText(message);
        // Set up next button
        setupNextButton(binding, bottomSheetDialog);

        // Set up explanation button
        setupExplanation(binding);

        // Set the icon based on whether the answer was correct or not
        binding.icon.setImageResource(isCorrect ? iconCorrect : iconIncorrect);

        // Show the dialog
        bottomSheetDialog.show();

    }

    private void setupNextButton(BottomSheetDialogBinding binding2, BottomSheetDialog bottomSheetDialog) {
        Button nextButton = binding2.getRoot().findViewById(R.id.button_next_question);
        NavController navController = NavHostFragment.findNavController(this);

        Log.d(TAG, "isLastQuestion: " + isLastQuestion);
        if (isLastQuestion) {
            nextButton.setText(com.audioquiz.designsystem.R.string.end_quiz);
            nextButton.setOnClickListener(v -> {
                viewModel.endQuiz();
                // Navigate to QuizResultsFragment
                navController.navigate(R.id.action_questionFragment_to_quizResultsFragment);
                // Close the dialog
                bottomSheetDialog.dismiss();
            });
        } else {
            nextButton.setOnClickListener(v -> {
                binding.submitButton.setEnabled(true); // Enable the submit button
                setOptionButtonsEnabled(true, allButtons); // Enable option buttons
                viewModel.loadNextQuestion(); // Load the next question
                // Close the dialog
                bottomSheetDialog.dismiss();
                // Reset selected button passed to viewmodel
                selectedOptionIndex = -1;   // Reset selected option index
                selectedOptionIndex2 = -1;   // Reset selected option index
                // selectedOptionText = "";   // Reset selected option text
                isAnswerSubmitted = false;  // Reset isAnswerSubmitted
            });
        }
    }


    private void setupExplanation(BottomSheetDialogBinding binding2) {
        FloatingActionButton explanationButton = binding2.getRoot().findViewById(R.id.button_explanation);
        View explanationBackground = binding2.getRoot().findViewById(R.id.bg_dialog_background);
        View space = binding2.getRoot().findViewById(R.id.space);
        View explanationLayout = binding2.getRoot().findViewById(R.id.included_explanation_layout);
        AutofitTextView explanationTextView = explanationLayout.findViewById(R.id.tv_explanation);

        String explanation = currentQuestion.getExplanation();
        explanationTextView.setText(explanation);
        AutofitHelper autofitHelper = AutofitHelper.create(explanationTextView);
        autofitHelper.setMinTextSize(16); // Set the minimum text size to 16sp
        autofitHelper.setMaxTextSize(30); // Set the maximum text size to 30sp

        if ("pitch".equals(category)) {
            explanationButton.setVisibility(View.GONE);
            explanationBackground.setVisibility(View.GONE);
            space.setVisibility(View.GONE);
        }

        explanationButton.setOnClickListener(v -> {
            if (!"pitch".equals(category)) {
                int visibility = explanationLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
                explanationLayout.setVisibility(visibility);
            }
        });

        // Start button animation
        startButtonAnimation(explanationButton);
    }

    private void startButtonAnimation(FloatingActionButton button) {
        // Convert 5dp to pixels
        float translationValue = 5 * getResources().getDisplayMetrics().density;

        // Create a TranslateAnimation that moves the button up and down
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -translationValue);
        animation.setDuration(1000); // Set the duration to 1500ms
        animation.setRepeatCount(Animation.INFINITE); // Repeat the animation infinitely
        animation.setRepeatMode(Animation.REVERSE); // Reverse the animation

        // Start the animation
        button.startAnimation(animation);
    }

    //Prevent Memory Leaks
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView called");
        // Nullify views
        binding.questionTextView.setText("");
        binding.submitButton.setEnabled(false);
        binding.quizProgressIndicator.setProgress(0);
        // Clear the list of buttons
        allButtons.clear();
        // Clear LiveData observers to avoid memory leaks and stale data
        viewModel.getQuestionLiveData().removeObservers(getViewLifecycleOwner());
    }
}
