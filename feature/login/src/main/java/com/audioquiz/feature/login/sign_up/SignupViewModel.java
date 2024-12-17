package com.audioquiz.feature.login.sign_up;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.audioquiz.core.domain.usecase.auth.UserAuthUseCaseFacade;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class SignupViewModel extends AndroidViewModel {
    //TODO change to extend ViewModel
    private final MutableLiveData<Object> firebaseUserLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();
    private final MutableLiveData<String> registrationErrorMessage = new MutableLiveData<>();
    private final UserAuthUseCaseFacade userAuthUseCaseFacade;
    private final CompositeDisposable disposable = new CompositeDisposable();
    private static final String TAG = "SignupViewModel";


    @Inject
    public SignupViewModel(Application application, UserAuthUseCaseFacade userAuthUseCaseFacade) {
        super(application);
        this.userAuthUseCaseFacade = userAuthUseCaseFacade;
    }
    public LiveData<Boolean> getIsRegistered() {
        return isRegistered;
    }

    public LiveData<String> getRegistrationErrorMessage() {
        return registrationErrorMessage;
    }

    public void registerUser(String email, String password, String username) {
        disposable.add(userAuthUseCaseFacade.registerUser(email, password, username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Timber.tag(TAG).d("Register User: " + email + ", " + password + ", " + username);
                    Timber.tag(TAG).d("Subscribe: %s", AndroidSchedulers.mainThread().toString());
                    isRegistered.setValue(result);
                }, this::setRegistrationErrorMessage));
    }

    private void setRegistrationErrorMessage(Throwable throwable) {
        registrationErrorMessage.setValue(throwable.getMessage());
    }

    public void resendVerificationEmail() {
        userAuthUseCaseFacade.sendVerificationEmail();
    }


    public void showConfirmationDialog() {
        // Show dialog to confirm email verification

    }

    public LiveData<Object> getFirebaseUserLiveData() {
        return firebaseUserLiveData;
    }
}