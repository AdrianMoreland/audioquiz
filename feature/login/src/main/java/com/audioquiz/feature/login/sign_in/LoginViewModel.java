package com.audioquiz.feature.login.sign_in;

import android.util.Patterns;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.audioquiz.api.datasources.google.GoogleApi;
import com.audioquiz.core.domain.usecase.auth.UserAuthUseCaseFacade;
import com.audioquiz.core.domain.usecase.user.profile.UserProfileUseCaseFacade;
import com.audioquiz.core.model.auth.LoggedInUser;
import com.audioquiz.core.model.auth.LoginType;
import com.audioquiz.core.model.util.Result;
import com.audioquiz.designsystem.base.SingleLiveEvent;
import com.audioquiz.feature.login.R;
import com.audioquiz.feature.login.domain.LoggedInUserView;
import com.audioquiz.feature.login.domain.LoginResult;
import com.audioquiz.feature.login.domain.LoginViewContract;
import com.audioquiz.feature.login.presentation.navigation.LoginCoordinatorEvent;
import com.audioquiz.library.util.RxExtensions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;


/**
 * A Subject that emits (multicasts) items to currently subscribed {@code Observer}s and terminal events to current
 * or late {@code Observer}s.
 * Example usage:
 * <pre> {@code
 * PublishSubject<Object> subject = PublishSubject.create();  //  observer1 will receive all onNext and onComplete events
 * } </pre>
 */
@HiltViewModel
public class LoginViewModel extends ViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final UserAuthUseCaseFacade userAuthUseCase;
    private final UserProfileUseCaseFacade userProfileUseCase;
    private final GoogleApi googleApi;

    private final SingleLiveEvent<LoginCoordinatorEvent> coordinatorEvent = new SingleLiveEvent<>();
    private final MutableLiveData<LoginViewContract.State> viewState = new MutableLiveData<>();
    private final MutableLiveData<LoginViewContract.Effect> effect = new SingleLiveEvent<>();

    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final MutableLiveData<String> passwordResetMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private final CompositeDisposable disposable = new CompositeDisposable();

    private String email;
    private String password;


    @Inject
    public LoginViewModel(
            UserAuthUseCaseFacade userAuthUseCase,
            UserProfileUseCaseFacade userProfileUseCase,
            GoogleApi googleApi) {
        this.userAuthUseCase = userAuthUseCase;
        this.userProfileUseCase = userProfileUseCase;
        this.googleApi = googleApi;
        viewState.setValue(createInitialState());
    }

    public LiveData<LoginCoordinatorEvent> navigationEvent() {
        return coordinatorEvent;
    }

    public LiveData<LoginViewContract.State> getViewState() {
        return viewState;
    }

    protected void setViewState(LoginViewContract.State newState) {
        Timber.tag(TAG).d("setting viewState : %s", newState);
        viewState.postValue(newState);
    }

    public LiveData<LoginViewContract.Effect> getViewEffect() {
        return effect;
    }

    protected void setViewEffect(LoginViewContract.Effect value) {
        Timber.tag(TAG).d("setting viewEffect : %s", value);
        effect.postValue(value);
    }

    protected <E extends LoginCoordinatorEvent> void sendCoordinatorEvent(E event) {
        Timber.tag(TAG).d("sending coordinatorEvent: %s", event);
        coordinatorEvent.postValue(event);
    }

    private void updateViewState(LoginViewContract.State currentState) {
        Integer usernameError = checkUsernameErrors(email) ? R.string.invalid_username : null;
        Integer passwordError = checkPasswordErrors(password) ? R.string.invalid_password : null;

        LoginViewContract.State newState = new LoginViewContract.State(currentState.isLoading(), email, password, usernameError, passwordError);
        Timber.tag(TAG).d("setting viewState : %s", newState);

        setViewState(newState);
    }

    public LoginViewContract.State createInitialState() {
        return new LoginViewContract.State(false, "", "", false); // Example initial state
    }


    public void process(LoginViewContract.Event viewEvent) {
        LoginViewContract.State currentState = getViewState().getValue();
        if (currentState != null) {
            if (viewEvent instanceof LoginViewContract.Event.OnLoginButtonClicked) {
                login(email, password, LoginType.EMAIL);
            } else if (viewEvent instanceof LoginViewContract.Event.OnGoogleSignInSuccess onGoogleSignInSuccess) {
                login(onGoogleSignInSuccess.getIdToken(), null, LoginType.GOOGLE);
            }else if (viewEvent instanceof LoginViewContract.Event.OnGoogleButtonClicked onGoogleButtonClicked) {
                signInWithGoogle(onGoogleButtonClicked.getActivity());
            } else if (viewEvent instanceof LoginViewContract.Event.OnGoogleSignInError onGoogleSignInError) {
                handleLoginFailure(onGoogleSignInError.getThrowable().toString());
            } else if (viewEvent instanceof LoginViewContract.Event.OnForgotPasswordLinkClicked) {
                sendCoordinatorEvent(new LoginCoordinatorEvent.ForgotPinCode());
            } else if (viewEvent instanceof LoginViewContract.Event.OnSignupLinkClicked) {
                sendCoordinatorEvent(new LoginCoordinatorEvent.OnSignupClicked());
            } else if (viewEvent instanceof LoginViewContract.Event.OnUserNameTextChanged onUserNameTextChanged) {
                email = onUserNameTextChanged.getUsername();
            } else if (viewEvent instanceof LoginViewContract.Event.OnPasswordTextChanged onPasswordTextChanged) {
                password = onPasswordTextChanged.getPassword();
            }
            updateViewState(currentState);
        }
    }

    private void signInWithGoogle(FragmentActivity activity) {
        Timber.tag(TAG).d("signInWithGoogle");
        try {
            googleApi.signIn(activity, new GoogleApi.GoogleSignInCallback() {
                @Override
                public void onSignInSuccess(String idToken) {
                    Timber.tag(TAG).d("Sign-in successful: %s", idToken);
                    process(new LoginViewContract.Event.OnGoogleSignInSuccess(idToken));
                }

                @Override
                public void onSignInError(Exception e) {
                    Timber.tag(TAG).e(e, "Sign-in failed: %s", e.getMessage());
                    process(new LoginViewContract.Event.OnGoogleSignInError(e));
                 //   Toast.makeText(getContext(), "Sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Timber.tag(TAG).e(e, "Error during sign-in"); // Log the exception
     //       Toast.makeText(getContext(), "Error during sign-in: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void login(String emailOrToken, String password, LoginType loginType) {
        disposable.add(
                userAuthUseCase.login(emailOrToken, password, loginType)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::handleLoginResult,
                                this::handleLoginError
                        )
        );
    }

    private void handleLoginResult(Result<?> result) {
        if (result instanceof Result.Success) {
            handleLoginSuccess((Result.Success<?>) result);
        } else {
            handleLoginFailure(new Result.Error(new Exception("Login failed")).toString());
        }
    }

    private void handleLoginSuccess(Result.Success<?> result) {
        setUserProfileData(result);
        updateViewState(new LoginViewContract.State(false, email, password, true));
        setViewEffect(new LoginViewContract.Effect.ShowSuccessToast("Login successful!"));
        sendCoordinatorEvent(new LoginCoordinatorEvent.OnLoginSuccess());
    }

    private void handleLoginFailure(String error) {
        loginResult.postValue(new LoginResult.Error(errorMessage.getValue()));
    }


    private void handleLoginError(Throwable error) {
        isLoading.postValue(false);
        Timber.tag("LoginViewModel").e(error, "Login error:");
        updateViewState(new LoginViewContract.State(false, "null", "null", false));
        setViewEffect(new LoginViewContract.Effect.ShowErrorToast("Login error: " + error.getMessage()));
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

    public void sendPasswordResetEmail(String email) {
        disposable.add(userAuthUseCase.sendPasswordResetEmail(email)
                .subscribe(
                        () -> passwordResetMessage.postValue("Password reset email sent. Please check your inbox."),
                        throwable -> passwordResetMessage.postValue("Failed to send password reset email.")
                ));
    }

    public LiveData<LoggedInUser> getUserProfileData() {
        Observable<LoggedInUser> userProfileObservable = getUserProfile();
        return RxExtensions.toLiveData(userProfileObservable);
    }

    private void setUserProfileData(Result.Success<?> result) {
        if (result.getData() instanceof LoggedInUser) {
            LoggedInUserView loggedInUserView = new LoggedInUserView(
                    System.currentTimeMillis(),
                    ((LoggedInUser) result.getData()).getDisplayName(),
                    ((LoggedInUser) result.getData()).getUserId(),
                    ((LoggedInUser) result.getData()).getEmail());
            Timber.tag("LoginViewModel").d("Logged in user: " + loggedInUserView.getUsername() + ((LoggedInUser) result.getData()).getDisplayName());
            loginResult.postValue(new LoginResult(loggedInUserView));
        } else {
            loginResult.postValue(new LoginResult.Error("Unexpected data type"));
        }
    }

    private Observable<LoggedInUser> getUserProfile() {
        return userProfileUseCase.getUserProfileSingle()
                .toObservable()
                .map(userProfile -> new LoggedInUser(
                        userProfile.getLastUpdated(),
                        userProfile.getUserId(),
                        userProfile.getUsername(),
                        userProfile.getProfileImage()
                ));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
        executorService.shutdown();
    }

}
