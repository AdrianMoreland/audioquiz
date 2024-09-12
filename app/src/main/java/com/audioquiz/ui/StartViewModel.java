package com.audioquiz.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrian.api.data.datasources.firebase_auth.AuthApi;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class StartViewModel extends ViewModel {
    private static final String TAG = "StartViewModel";
    private final AuthApi authApi;
    private final MutableLiveData<Boolean> isUserAuthorizedLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public StartViewModel(AuthApi authApi) {
        Log.d(TAG, "StartViewModel initialized");
        this.authApi = authApi;
        checkAuthorization();
    }

    private void checkAuthorization() {
        Log.d(TAG, "checkAuthorization called");
        disposables.add(
                authApi.isAuthorized()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isUserAuthorizedLiveData::postValue, throwable -> Log.e(TAG, "Error checking authorization", throwable))
        );
    }

    public LiveData<Boolean> getIsUserAuthorizedLiveData() {
        Boolean value = isUserAuthorizedLiveData.getValue();
        if (value == null || !value) {
            Log.d(TAG, "getIsUserAuthorizedLiveData: false");
            isUserAuthorizedLiveData.setValue(false);
        } else {
            Log.d(TAG, "getIsUserAuthorizedLiveData: true");
            isUserAuthorizedLiveData.setValue(true);
        }
        Log.d(TAG, "getIsUserAuthorizedLiveData: " + isUserAuthorizedLiveData.getValue());
        return isUserAuthorizedLiveData;
    }

}
