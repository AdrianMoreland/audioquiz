package com.audioquiz.ui.component.dialog;

import android.app.Activity;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class DialogComponent implements DialogComponentApi {

    @Override
    public void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialog(Activity activity, String message, Runnable callback) {
        new AlertDialog.Builder(activity)
                .setTitle("Info")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> {
                    if (callback != null) {
                        callback.run();
                    }
                    dialog.dismiss();
                })
                .create()
                .show();
    }
}
