package com.audioquiz.api.datasources.google;

import androidx.fragment.app.FragmentActivity;

public interface GoogleApi {

  void signIn (FragmentActivity fragmentActivity, GoogleApi.GoogleSignInCallback googleSignInCallback);

  interface GoogleSignInCallback {
    void onSignInSuccess(String idToken);
    void onSignInError(Exception e);
  }

}
