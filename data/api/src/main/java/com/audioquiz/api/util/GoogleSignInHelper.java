package com.audioquiz.api.util;


import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import io.reactivex.rxjava3.core.Observable;


public interface GoogleSignInHelper {

    void signIn (FragmentActivity fragmentActivity, GoogleSignInCallback googleSignInCallback);

    Observable<String> fetchToken(Context appContext, String webClientId);

    interface GoogleSignInCallback {
        void onSignInSuccess(String idToken);
        void onSignInError(Exception e);
    }
}