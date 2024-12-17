package com.audioquiz.data.remote.provider;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class AuthProvider {
    private FirebaseAuth mAuth;

    @Inject
    public AuthProvider() {
        Log.d("AuthProvider", "AuthProvider created");
    }

    public void init() {
        setAuth();
    }

    public FirebaseAuth getAuth() {
        if (mAuth == null) {
            setAuth();
        }
        return mAuth;
    }

    private void setAuth() {
        this.mAuth = FirebaseAuth.getInstance();
    }
}