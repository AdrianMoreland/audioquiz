package com.audioquiz.feature.settings.view.viewmodel;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataKt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audioquiz.core.domain.usecase.auth.UserAuthUseCaseFacade;
import com.audioquiz.designsystem.base.SingleLiveEvent;
import com.audioquiz.designsystem.R;
import com.audioquiz.feature.settings.domain.SettingsViewContract;
import com.audioquiz.feature.settings.presentation.navigation.SettingsCoordinatorEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

@HiltViewModel
public class SettingsViewModel extends ViewModel {
    private static final String TAG = "SettingsViewModel";
    private final UserAuthUseCaseFacade authUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<SettingsViewContract.State> viewState = new MutableLiveData<>();
    private final MutableLiveData<SettingsViewContract.Effect> effect = new SingleLiveEvent<>();
    private final SingleLiveEvent<SettingsCoordinatorEvent> coordinatorEvent = new SingleLiveEvent<>();

    private final MutableLiveData<Boolean> logoutSuccess = new MutableLiveData<>(); // LiveData to hold logout success status
    private String username;
    private String email;
    private String password;

    public LiveData<Boolean> getLogoutSuccess() { // Expose LiveData to the fragment
        return logoutSuccess;
    }


    @Inject
    public SettingsViewModel(UserAuthUseCaseFacade authUseCase) {
        this.authUseCase = authUseCase;
        logoutSuccess.setValue(false); // Initialize LiveData with false
        Timber.tag("SettingsViewModel").d("SettingsViewModel: ");
    }

    public LiveData<SettingsCoordinatorEvent> navigationEvent() {
        return coordinatorEvent;
    }
    public LiveData<SettingsViewContract.State> getViewState() {
        return viewState;
    }

    public LiveData<SettingsViewContract.Effect> getViewEffect() {
        return effect;
    }

    protected <E extends SettingsCoordinatorEvent> void sendCoordinatorEvent(E event) {
        Timber.tag(TAG).d("sending coordinatorEvent: %s", event);
        coordinatorEvent.postValue(event);
    }

    public void process(SettingsViewContract.Event viewEvent) {
        SettingsViewContract.State currentState = getViewState().getValue();
        if (currentState != null) {
            if (viewEvent instanceof SettingsViewContract.Event.OnConfirmChangesButtonClicked) {
                confirmChanges(username, email);
            } else if (viewEvent instanceof SettingsViewContract.Event.OnChangePasswordButtonClicked) {
                sendCoordinatorEvent(new SettingsCoordinatorEvent.OnDeleteAccountClicked());
            }  else if (viewEvent instanceof SettingsViewContract.Event.OnLogoutButtonClicked) {
                logout();
            } else if (viewEvent instanceof SettingsViewContract.Event.OnDeleteAccountButtonClicked) {
                sendCoordinatorEvent(new SettingsCoordinatorEvent.OnDeleteAccountClicked());
            }  else if (viewEvent instanceof SettingsViewContract.Event.OnUserNameTextChanged onUserNameTextChanged) {
                username = onUserNameTextChanged.getUsername();
            } else if (viewEvent instanceof SettingsViewContract.Event.OnEmailTextChanged onEmailTextChanged) {
                email = onEmailTextChanged.getPassword();
            }
            updateViewState(currentState);
        }
    }

    private void updateViewState(SettingsViewContract.State currentState) {
        Integer usernameError = checkUsernameErrors(username) ? R.string.invalid_username : null;
        Integer passwordError = checkPasswordErrors(password) ? R.string.invalid_password : null;

        SettingsViewContract.State newState = new SettingsViewContract.State(currentState.isLoading(), username, password, usernameError, passwordError);
        Timber.tag(TAG).d("setting viewState : %s", newState);
        viewState.postValue(newState);
    }

    private boolean checkUsernameErrors(String username) {
        if (username == null || username.trim().isEmpty()) {
            return true; // Error: username is null or empty
        }
        return username.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(username).matches(); // Error: invalid email format
    }

    private boolean checkPasswordErrors(String password) {
        return password == null || password.trim().length() <= 5;
    }

    private void confirmChanges(String username, String password) {

    }

    public LiveDataKt getIsPasswordChanged() {
        return null;
    }

    public void changePassword(String currentPasswordText, String newPasswordText, String confirmPassword) {

    }

    public void deleteUserAccount(String password) {

    }

    public LiveData<Boolean> getIsAccountDeleted() {
        return new MutableLiveData<Boolean>(false);
    }

    public void updateUsername(String newUsername) {

    }

    public void logout() {
        disposables.add( // Add to disposables to manage the subscription
                authUseCase.logout()
                        .subscribeOn(Schedulers.io()) // Perform logout on a background thread
                        .observeOn(AndroidSchedulers.mainThread()) // Switch back to the main thread for navigation
                        .subscribe(
                                () -> {
                                    Timber.tag(TAG).d("logout success");
                                    logoutSuccess.setValue(true);
                                },
                                throwable -> {
                                    Timber.tag(TAG).e(throwable, "logout: ");
                                    logoutSuccess.setValue(false);
                                }
                        )
        );
    }

    public void updateEmail(String newEmail) {

    }

    public String getCurrentUserName() {
        return null;
    }

    public String getCurrentUserEmail() {
        return null;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose(); // Dispose of subscriptions when ViewModel is cleared
    }

}
