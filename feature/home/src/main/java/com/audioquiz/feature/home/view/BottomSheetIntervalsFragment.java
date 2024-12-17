package com.audioquiz.feature.home.view;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audioquiz.feature.home.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BottomSheetIntervalsFragment extends BottomSheetDialogFragment {
//    private QuestionViewModel questionViewModel;

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
            behavior.setState(BottomSheetBehavior. STATE_EXPANDED);
            behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        } else {
            Log.d("BottomSheetIntervalsFragment", "onStart: bottomSheet is null");
        }
    }


    @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.bottom_sheet_root_note, container, false);
            // Create the factory
            // Use the factory to get the QuestionViewModel
         //   questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);


            navigateToQuiz(view, R.id.btn_note_c, "C4");
            navigateToQuiz(view, R.id.btn_note_c_sharp, "C#4");
            navigateToQuiz(view, R.id.btn_note_d, "D4");
            navigateToQuiz(view, R.id.btn_note_d_sharp, "D#4");
            navigateToQuiz(view, R.id.btn_note_e, "E4");
            navigateToQuiz(view, R.id.btn_note_f, "F4");
            navigateToQuiz(view, R.id.btn_note_f_sharp, "F#4");
            navigateToQuiz(view, R.id.btn_note_g, "G4");
            navigateToQuiz(view, R.id.btn_note_g_sharp, "G#4");
            navigateToQuiz(view, R.id.btn_note_a, "A4");
            navigateToQuiz(view, R.id.btn_note_a_sharp, "A#4");
            navigateToQuiz(view, R.id.btn_note_b, "B4");

            return view;
        }


    private void navigateToQuiz(View view, int buttonId, String rootNote) {
        String category = "intervals";
        int chapter = 1;
        MaterialButton noteButton = view.findViewById(buttonId);
        noteButton.setOnClickListener(v -> {
            Log.d("BottomSheetIntervalsFragment", "CategoryUi: " + category);
            //call the startQuiz method from QuestionViewModel passing the selected Root Note instead of the quizType
       //     questionViewModel.startQuiz(category, chapter, rootNote);
            // Disable the button
            noteButton.setEnabled(false);
            Bundle args = new Bundle();
            args.putString("category", category);
            args.putInt("level", chapter);
          //  NavHostFragment.findNavController(this).navigate(R.id.action_intervalsFragment_to_questionFragment, args);
            // Re-enable the button after 1 second
            new Handler().postDelayed(() -> noteButton.setEnabled(true), 1000); // Delay in milliseconds
        });
    }
    
}
