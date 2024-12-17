package com.audioquiz.feature.settings.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.audioquiz.feature.settings.R;


public class DeleteAccountDialogFragment extends DialogFragment {

    public interface OnPasswordConfirmedListener {
        void onPasswordConfirmed(String password);
    }

    private OnPasswordConfirmedListener listener;

    public void setOnPasswordConfirmedListener(OnPasswordConfirmedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_delete_account_dialog, null);
        final EditText passwordEditText = view.findViewById(R.id.password_edit_text);

        Button cancelButton = view.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(v -> {
            // User cancelled the dialog
            dismiss();
        });

        Button deleteButton = view.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(v -> {
            String password = passwordEditText.getText().toString();
            if (listener != null) {
                listener.onPasswordConfirmed(password);
            }
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}