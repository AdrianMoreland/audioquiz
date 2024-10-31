package com.audioquiz.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import timber.log.Timber;

@HiltViewModel
public class StartViewModel extends ViewModel {
    private static final String TAG = "StartViewModel";
    private final AuthApi authApi;
    private final MutableLiveData<Boolean> isUserAuthorizedLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public StartViewModel(AuthApi authApi) {
        Timber.tag(TAG).d("StartViewModel initialized");
        this.authApi = authApi;
        checkAuthorization();
    }

    private void checkAuthorization() {
        Timber.tag(TAG).d("checkAuthorization called");
        disposables.add(
                authApi.isAuthorized()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isUserAuthorizedLiveData::postValue, throwable -> Timber.tag(TAG).e(throwable, "Error checking authorization"))
        );
    }

    public LiveData<Boolean> getIsUserAuthorizedLiveData() {
        Boolean value = isUserAuthorizedLiveData.getValue();
        if (value == null || !value) {
            Timber.tag(TAG).d("getIsUserAuthorizedLiveData: false");
            isUserAuthorizedLiveData.setValue(false);
        } else {
            Timber.tag(TAG).d("getIsUserAuthorizedLiveData: true");
            isUserAuthorizedLiveData.setValue(true);
        }
        Timber.tag(TAG).d("getIsUserAuthorizedLiveData: %s", isUserAuthorizedLiveData.getValue());
        return isUserAuthorizedLiveData;
    }

}
