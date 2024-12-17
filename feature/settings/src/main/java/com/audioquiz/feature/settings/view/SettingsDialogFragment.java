package com.audioquiz.feature.settings.view;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.audioquiz.feature.settings.databinding.FragmentSettingsDialogBinding;
import com.audioquiz.feature.settings.view.viewmodel.SettingsViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SettingsDialogFragment extends DialogFragment {
    private static final String TAG = "SettingsDialogFragment";

    SettingsViewModel viewModel;

    private FragmentSettingsDialogBinding binding;

    public SettingsDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsDialogBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        // Add the logic to access binding here
        TextInputEditText usernameEditText = binding.usernameChangeEditText;
        TextInputEditText emailEditText = binding.emailChangeEditText;
        Button confirmChangeButton = binding.btnConfirmChange;
        confirmChangeButton.setOnClickListener(v -> {
            String newUsername = Objects.requireNonNull(usernameEditText.getText()).toString();
            String newEmail = Objects.requireNonNull(emailEditText.getText()).toString();
            viewModel.updateEmail(newEmail);
            viewModel.updateUsername(newUsername);
        });

        // Get the current user's details
        String currentUserName = viewModel.getCurrentUserName();
        String currentUserEmail = viewModel.getCurrentUserEmail();
        // Set the hint of the username and email EditTexts to the current username and email
        if (currentUserName != null) {
            usernameEditText.setHint(currentUserName);
            emailEditText.setHint(currentUserEmail);
        }


        binding.btnChangePassword.setOnClickListener(v -> {
            PasswordChangeSheetFragment bottomSheet = new PasswordChangeSheetFragment();
            bottomSheet.show(getChildFragmentManager(), "passwordBottomSheet");
        });

        binding.btnLogout.setOnClickListener(v -> {
            viewModel.logout();
        });

        binding.btnDeleteAccount.setOnClickListener(v -> {
            DeleteAccountDialogFragment deleteAccountDialogFragment = new DeleteAccountDialogFragment();
            deleteAccountDialogFragment.setOnPasswordConfirmedListener(password -> {
                if (password != null && !password.isEmpty()) {
                    viewModel.deleteUserAccount(password);
                } else {
                    // Handle case where password is null or empty
                    Timber.tag(TAG).d("Failed to delete user home: Password is missing.");
                }
            });
            deleteAccountDialogFragment.show(getChildFragmentManager(), "deleteAccountDialog");
        });

        viewModel.getLogoutSuccess().observe(getViewLifecycleOwner(), isLoggedOut -> {
            if (Boolean.TRUE.equals(isLoggedOut)) {
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        viewModel.getIsAccountDeleted().observe(getViewLifecycleOwner(), isDeleted -> {
            if (Boolean.TRUE.equals(isDeleted)) {
                Navigation.findNavController(requireView()).popBackStack();
            }
        });

        // Now that the binding has been accessed, set the view.
        ((AlertDialog) getDialog()).setView(binding.getRoot());
    }

    public void onBackButtonPressed(View view) {
        Navigation.findNavController(view).popBackStack();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
