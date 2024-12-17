package com.audioquiz.data.remote.datasource;
import androidx.lifecycle.MutableLiveData;

import com.audioquiz.core.model.util.Result;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class DataProvider {

    public enum AuthState {
        AUTHENTICATED, SIGNED_IN, SIGNED_OUT, IDLE, LOADING
    }

    public static final MutableLiveData<Result<FirebaseUser>> oneTapSignInResponse =
            new MutableLiveData<>(new Result.Success<>(null)); // Specify type parameters
    public static final MutableLiveData<Result<FirebaseUser>> anonymousSignInResponse =
            new MutableLiveData<>(new Result.Success<>(null));
    public static final MutableLiveData<Result<FirebaseUser>> googleSignInResponse =
            new MutableLiveData<>(new Result.Success<>(null));
    public static final MutableLiveData<Result<Boolean>> signOutResponse =
            new MutableLiveData<>(new Result.Success<>(false));
    public static final MutableLiveData<Result<Boolean>> deleteAccountResponse =
            new MutableLiveData<>(new Result.Success<>(false));

    public static final MutableLiveData<FirebaseUser> user = new MutableLiveData<>(null);
    public static final MutableLiveData<Boolean> isAuthenticated = new MutableLiveData<>(false);
    public static final MutableLiveData<Boolean> isAnonymous = new MutableLiveData<>(false);
    public static final MutableLiveData<AuthState> authState = new MutableLiveData<>(AuthState.SIGNED_OUT);
    public static final MutableLiveData<AuthResult> authResult = new MutableLiveData<>(null);

    public static void updateAuthState(FirebaseUser user, AuthResult result) {
        DataProvider.user.postValue(user);
        isAuthenticated.postValue(user != null);
        isAnonymous.postValue(user != null && user.isAnonymous());

        authResult.postValue(result);

        if (isAuthenticated.getValue() != null && isAuthenticated.getValue()) {
            if (isAnonymous.getValue() != null && isAnonymous.getValue()) {
                authState.postValue(AuthState.AUTHENTICATED);
            } else {
                authState.postValue(AuthState.SIGNED_IN);
            }
        } else {
            authState.postValue(AuthState.SIGNED_OUT);
        }
    }
}