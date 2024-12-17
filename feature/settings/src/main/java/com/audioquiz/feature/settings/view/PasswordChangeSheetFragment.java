package com.audioquiz.feature.settings.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.audioquiz.feature.settings.R;
import com.audioquiz.feature.settings.view.viewmodel.SettingsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class PasswordChangeSheetFragment extends BottomSheetDialogFragment {

    private SettingsViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_change_sheet, container, false);

        TextInputLayout confirmNewPassword = view.findViewById(R.id.change_confirm_password);
        TextInputLayout currentPassword = view.findViewById(R.id.current_password);
        TextInputLayout newPassword = view.findViewById(R.id.change_password);

/*        SettingsViewModel settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        viewModel.getIsPasswordChanged().observe(getViewLifecycleOwner(), isChanged -> {
            if (Boolean.TRUE.equals(isChanged)) {
                Log.d(TAG, "isPasswordChanged is true");
                Toast.makeText(getContext(), "Password successfully changed", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Calling dismiss()");
                dismiss();
            } else {
                Toast.makeText(getContext(), "Password change failed", Toast.LENGTH_SHORT).show();
            }
            return;
        });*/

        Button confirmButton = view.findViewById(R.id.btn_password_change_confirm);
        confirmButton.setOnClickListener(v -> {
            String currentPasswordText = Objects.requireNonNull(currentPassword.getEditText()).getText().toString();
            String newPasswordText = Objects.requireNonNull(newPassword.getEditText()).getText().toString();
            String confirmPassword = Objects.requireNonNull(confirmNewPassword.getEditText()).getText().toString();

            if (currentPasswordText.isEmpty() || newPasswordText.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(getContext(), "All fields must be filled", Toast.LENGTH_SHORT).show();
            } else if (!newPasswordText.equals(confirmPassword)) {
                Toast.makeText(getContext(), "New password and confirmed password must match", Toast.LENGTH_SHORT).show();
            } else {
                viewModel.changePassword(currentPasswordText, newPasswordText, confirmPassword);
            }
        });
        return view;
    }
}