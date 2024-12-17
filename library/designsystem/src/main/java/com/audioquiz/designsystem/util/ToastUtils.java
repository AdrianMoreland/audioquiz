package com.audioquiz.designsystem.util;

import android.app.Activity;
import android.widget.Toast;

public class ToastUtils {
    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }
}
