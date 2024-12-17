package com.audioquiz.data.repository.auth;

import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;

import com.audioquiz.api.datasources.firebase_auth.AuthApi;
import com.audioquiz.api.datasources.google.GoogleApi;
import com.audioquiz.api.exceptions.InvalidLoginTypeException;
import com.audioquiz.core.domain.repository.auth.AuthRepository;
import com.audioquiz.core.model.auth.LoggedInUser;
import com.audioquiz.core.model.auth.LoginType;
import com.audioquiz.core.model.util.Result;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthRepositoryImpl implements AuthRepository {
    private final AuthApi authApi;
    private final GoogleApi googleApi;
    private static final String TAG = "AuthRepositoryImpl";
    private LoggedInUser user = null;
    private String userId = null;
    private final ExecutorService executorService;
    private final ThreadPoolExecutor executor;
    private final SharedPreferences sharedPreferences;

    private final MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();
    private final MutableLiveData<String> registrationErrorMessage = new MutableLiveData<>();

    @Inject
    public AuthRepositoryImpl(AuthApi authApi,
                              GoogleApi googleApi,
                              ExecutorService executorService,
                              SharedPreferences sharedPreferences) {
        this.authApi = authApi;
        this.googleApi = googleApi;
        this.executorService = executorService;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        this.sharedPreferences = sharedPreferences;
    }

    public void init() {
        userId = getFirebaseUserId();
    }

    @Override
    public Single<Boolean> sync() {
        return null;
    }

    @Override
    public Completable logoutUser() {
        return authApi.logout();
    }


    /**
     * Retrieves the Firebase User ID of the currently logged-in user.
     *
     * @return The Firebase User ID as a String, or null if no user is logged in.
     */
    private String getFirebaseUserId() {
        return authApi.getFirebaseUserId();
    }

    /**
     * Sets the logged-in user for the repository.
     *
     * @param user The {@link LoggedInUser} object representing the logged-in user.
     */
    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
    }

    /**
     * Performs a login operation based on the provided login type.
     *
     * @param email The email or ID token used for authentication.
     * @param password       The password used for email authentication.
     * @param loginType      The type of login operation to perform ({@link LoginType}).
     * @return An Observable emitting a {@link Result} object representing the outcome of the login operation.
     * @throws InvalidLoginTypeException If an invalid login type is provided.
     */
    @Override
    public Single<Result<?>> login(String emailOrToken, String password, LoginType loginType) {
        Log.d(TAG, "login called: " + loginType);
        return switch (loginType) {
            case EMAIL -> authApi.signInWithEmailAndPassword(emailOrToken, password)
                    .doOnSuccess(this::handleLoginResult);
            case GOOGLE -> authApi.signInWithGoogle(emailOrToken)
                    .doOnSuccess(this::handleLoginResult);
        };
    }

    /**
     * Handles the result of a login operation.
     * If the result is successful, sets the logged-in user.
     * If the result is an error, logs the error message.
     *
     * @param result The {@link Result} object representing the outcome of the login operation.
     * @param <T>    The type of data associated with the result.
     */
    private <T> void handleLoginResult(Result<T> result) {
        if (result instanceof Result.Success<?> success) {
            Log.d(TAG, "handleLoginResult: " + success.getData());
            setLoggedInUser((LoggedInUser) success.getData());
            updateUserLoggedInStatus(true);
        } else if (result instanceof Result.Error error) {
            updateUserLoggedInStatus(false);
            Log.e("LoginRepository", "handleLoginResult error: " + error.getException());
        }
    }

    private void updateUserLoggedInStatus(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("user_logged_in", isLoggedIn);
        editor.apply();
    }

    @Override
    public Observable<Boolean> registerUser(String email, String password, String username) {
        return applySchedulers(authApi.registerUser(email, password, username)
                .doOnNext(result -> {
                    Log.d(TAG, "registerUser: " + result);
                    isRegistered.setValue(result);
                }));
    }

    /**
     * Applies schedulers to an Observable to perform operations on a background thread
     * and observe the results on the main thread.
     *
     * @param observable The Observable to apply schedulers to.
     * @param <T>        The type of data emitted by the Observable.
     * @return The Observable with schedulers applied.
     */
    private <T> Observable<T> applySchedulers(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.from(executorService))
                .observeOn(AndroidSchedulers.mainThread());
    }





    @Override
    public Completable sendPasswordResetEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return authApi.sendPasswordResetEmail(email);
        } else {
            return Completable.error(new IllegalArgumentException("Invalid email address"));
        }
    }

    @Override
    public Completable sendVerificationEmail() {
        return authApi.sendVerificationEmail();
    }

    private void handleRegistrationError(Exception exception) {
        if (exception != null) {
            String error = exception.getMessage();
            if (Objects.requireNonNull(error).contains("The email address is already in use")) {
                registrationErrorMessage.setValue("The email address is already in use.");
            } else {
                registrationErrorMessage.setValue(error);
            }
        }
        isRegistered.setValue(false);
    }

    public Task<Void> changePassword(String currentPassword, String newPassword) {
        return authApi.changePassword(currentPassword, newPassword);
    }

    public void deleteUserAccount(String password) {
        authApi.deleteUserAccount(password);
    }

}
