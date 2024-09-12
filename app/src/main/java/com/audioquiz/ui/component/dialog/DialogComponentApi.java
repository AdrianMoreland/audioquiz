package com.audioquiz.ui.component.dialog;

import android.app.Activity;

public interface DialogComponentApi {

    void showToast(Activity activity, String message);

    void showDialog(Activity activity, String message, Runnable callback);
}
