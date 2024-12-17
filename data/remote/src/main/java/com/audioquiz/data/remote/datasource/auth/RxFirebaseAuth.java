package com.audioquiz.data.remote.datasource.auth;

import androidx.annotation.NonNull;

import com.google.firebase.auth.ActionCodeResult;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeEmitter;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Cancellable;

public class RxFirebaseAuth {

    /**
     * Asynchronously signs in as an anonymous user.
     * If there is already an anonymous user signed in, that user will be returned; otherwise, a new anonymous user identity will be created and returned.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @return a {@link Maybe} which emits an {@link AuthResult} if success.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Maybe<AuthResult> signInAnonymously(@NonNull final FirebaseAuth firebaseAuth) {
        return Maybe.create(new MaybeOnSubscribe<AuthResult>() {
            @Override
            public void subscribe(MaybeEmitter<AuthResult> emitter) throws Exception {
                RxHandler.assignOnTask(emitter, firebaseAuth.signInAnonymously());
            }
        });
    }

    /**
     * Asynchronously signs in using an email and password.
     * Fails with an error if the email address and password do not match.
     * <p>
     * Note: The user's password is NOT the password used to access the user's email account.
     * The email address serves as a unique identifier for the user, and the password is used to access the user's account in your Firebase project.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param email        email to login.
     * @param password     password to login.
     * @return a {@link Maybe} which emits an {@link AuthResult} if success.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Maybe<AuthResult> signInWithEmailAndPassword(@NonNull final FirebaseAuth firebaseAuth,
                                                               @NonNull final String email,
                                                               @NonNull final String password) {
        return Maybe.create(emitter -> RxHandler.assignOnTask(emitter, firebaseAuth.signInWithEmailAndPassword(email, password)));
    }

    /**
     * Asynchronously signs in with the given credentials.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param credential   The auth credential. Value must not be null.
     * @return a {@link Maybe} which emits an {@link AuthResult} if success.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Maybe<AuthResult> signInWithCredential(@NonNull final FirebaseAuth firebaseAuth,
                                                         @NonNull final AuthCredential credential) {
        return Maybe.create(new MaybeOnSubscribe<AuthResult>() {
            @Override
            public void subscribe(MaybeEmitter<AuthResult> emitter) throws Exception {
                RxHandler.assignOnTask(emitter, firebaseAuth.signInWithCredential(credential));
            }
        });
    }

    /**
     * Asynchronously signs in using a custom token.
     * <p>
     * Custom tokens are used to integrate Firebase Auth with existing auth systems, and must be generated by the auth backend.
     * <p>
     * Fails with an error if the token is invalid, expired, or not accepted by the Firebase Auth service.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param token        The custom token to sign in with.
     * @return a {@link Maybe} which emits an {@link AuthResult} if success.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Maybe<AuthResult> signInWithCustomToken(@NonNull final FirebaseAuth firebaseAuth,
                                                          @NonNull final String token) {
        return Maybe.create(new MaybeOnSubscribe<AuthResult>() {
            @Override
            public void subscribe(MaybeEmitter<AuthResult> emitter) throws Exception {
                RxHandler.assignOnTask(emitter, firebaseAuth.signInWithCustomToken(token));
            }
        });
    }

    /**
     * Creates a new user account associated with the specified email address and password.
     * <p>
     * On successful creation of the user account, this user will also be signed in to your application.
     * <p>
     * User account creation can fail if the account already exists or the password is invalid.
     * <p>
     * Note: The email address acts as a unique identifier for the user and enables an email-based password reset.
     * This function will create a new user account and set the initial user password.
     * <p>
     * Custom tokens are used to integrate Firebase Auth with existing auth systems, and must be generated by the auth backend.
     * <p>
     * Fails with an error if the token is invalid, expired, or not accepted by the Firebase Auth service.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param email        The user's email address.
     * @param password     The user's chosen password.
     * @return a {@link Maybe} which emits an {@link AuthResult} if success.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Maybe<AuthResult> createUserWithEmailAndPassword(@NonNull final FirebaseAuth firebaseAuth,
                                                                   @NonNull final String email,
                                                                   @NonNull final String password) {
        return Maybe.create(new MaybeOnSubscribe<AuthResult>() {
            @Override
            public void subscribe(MaybeEmitter<AuthResult> emitter) throws Exception {
                RxHandler.assignOnTask(emitter, firebaseAuth.createUserWithEmailAndPassword(email, password));
            }
        });
    }

    /**
     * Gets the list of provider IDs that can be used to sign in for the given email address. Useful for an "identifier-first" sign-in flow.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param email        An email address.
     * @return a {@link Maybe} which emits an {@link SignInMethodQueryResult} if success.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Maybe<SignInMethodQueryResult> fetchSignInMethodsForEmail(@NonNull final FirebaseAuth firebaseAuth,
                                                                            @NonNull final String email) {
        return Maybe.create(new MaybeOnSubscribe<SignInMethodQueryResult>() {
            @Override
            public void subscribe(MaybeEmitter<SignInMethodQueryResult> emitter) {
                RxHandler.assignOnTask(emitter, firebaseAuth.fetchSignInMethodsForEmail(email));
            }
        });
    }

    /**
     * Sends a password reset email to the given email address.
     * <p>
     * To complete the password reset, call {@link FirebaseAuth#confirmPasswordReset(String, String)} with the code supplied in
     * the email sent to the user, along with the new password specified by the user.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param email        The email address with the password to be reset.
     * @return a {@link Completable} which emits when the action is completed.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Completable sendPasswordResetEmail(@NonNull final FirebaseAuth firebaseAuth,
                                                     @NonNull final String email) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
             //   RxCompletableHandler.assignOnTask(emitter, firebaseAuth.sendPasswordResetEmail(email));
            }
        });
    }

    /**
     * Asynchronously sets the provided user as currentUser on the current Auth instance. A new instance copy of the user provided will be made and set as currentUser.
     * <p>
     * This will trigger firebase.auth.Auth#onAuthStateChanged and firebase.auth.Auth#onIdTokenChanged listeners like other sign in methods.
     * @param firebaseAuth firebaseAuth instance.
     * @param newUser The new user to update the current instance.
     * @return a {@link Completable} which emits when the action is completed.
     * @see <a href="https://firebase.google.com/docs/reference/android/com/google/firebase/auth/FirebaseAuth">Firebase Auth API</a>
     */
    @NonNull
    public static Completable updateCurrentUser(@NonNull final FirebaseAuth firebaseAuth,
                                                     @NonNull final FirebaseUser newUser) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
            //    RxCompletableHandler.assignOnTask(emitter, firebaseAuth.updateCurrentUser(newUser));
            }
        });
    }

    /**
     * Observable which track the auth changes of {@link FirebaseAuth} to listen when an user is logged or not.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @return an {@link Observable} which emits every time that the {@link FirebaseAuth} state change.
     */
    @NonNull
    public static Observable<FirebaseAuth> observeAuthState(@NonNull final FirebaseAuth firebaseAuth) {

        return Observable.create(new ObservableOnSubscribe<FirebaseAuth>() {
            @Override
            public void subscribe(final ObservableEmitter<FirebaseAuth> emitter) throws Exception {
                final FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        emitter.onNext(firebaseAuth);
                    }
                };
                firebaseAuth.addAuthStateListener(authStateListener);
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        firebaseAuth.removeAuthStateListener(authStateListener);
                    }
                });
            }
        });
    }

    /**
     * Checks that the code given is valid. This code will have been generated
     * by {@link FirebaseAuth#sendPasswordResetEmail(String)} or {@link com.google.firebase.auth.FirebaseUser#sendEmailVerification()} valid for a single use.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param code         generated code by firebase.
     * @return a {@link Maybe} which emits when the action is completed.
     */
    @NonNull
    public static Maybe<ActionCodeResult> checkActionCode(@NonNull final FirebaseAuth firebaseAuth,
                                                          @NonNull final String code) {
        return Maybe.create(new MaybeOnSubscribe<ActionCodeResult>() {
            @Override
            public void subscribe(MaybeEmitter<ActionCodeResult> emitter) throws Exception {
                RxHandler.assignOnTask(emitter, firebaseAuth.checkActionCode(code));
            }
        });
    }

    /**
     * Changes the user's password to newPassword for the account for which the code is valid.
     * Code validity can be checked with {@link FirebaseAuth#verifyPasswordResetCode(String)}.
     * This use case is only valid for signed-out users, and behavior is undefined for signed-in users.
     * Password changes for signed-in users should be made using {@link com.google.firebase.auth.FirebaseUser#updatePassword(String)}.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param code         generated code by firebase.
     * @param newPassword  new password for the user.
     * @return a {@link Completable} which emits when the action is completed.
     */
    @NonNull
    public static Completable confirmPasswordReset(@NonNull final FirebaseAuth firebaseAuth,
                                                   @NonNull final String code,
                                                   @NonNull final String newPassword) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
              //  RxCompletableHandler.assignOnTask(emitter, firebaseAuth.confirmPasswordReset(code, newPassword));
            }
        });
    }

    /**
     * Applies the given code, which can be any out of band code which is valid according
     * to {@link FirebaseAuth#checkActionCode(String)} that does not also pass {@link FirebaseAuth#{verifyPasswordResetCode(String)},
     * which requires an additional parameter.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param code         generated code by firebase.
     * @return a {@link Completable} which emits when the action is completed.
     */
    @NonNull
    public static Completable applyActionCode(@NonNull final FirebaseAuth firebaseAuth,
                                              @NonNull final String code) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
//                RxCompletableHandler.assignOnTask(emitter, firebaseAuth.applyActionCode(code));
            }
        });
    }

    /**
     * Checks that the code is a valid password reset out of band code.
     * This code will have been generated by a call to {@link FirebaseAuth#sendPasswordResetEmail(String)}, and is valid for a single use.
     *
     * @param firebaseAuth firebaseAuth instance.
     * @param code         generated code by firebase.
     * @return a {@link Maybe} which emits when the action is completed.
     */
    @NonNull
    public static Maybe<String> verifyPasswordResetCode(@NonNull final FirebaseAuth firebaseAuth,
                                                        @NonNull final String code) {
        return Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {
                RxHandler.assignOnTask(emitter, firebaseAuth.verifyPasswordResetCode(code));
            }
        });
    }

    /**
     * Registers a listener to changes in the ID token state.
     * There can be more than one listener registered at the same time. The listeners are called asynchronously, possibly on a different thread.
     * <p>
     * Authentication state changes are:
     * <p>
     * -When a user signs in
     * -When the current user signs out
     * -When the current user changes
     * -When there is a change in the current user's token
     * <p>
     * Use RemoveIdTokenListener to unregister a listener.
     *
     * @param firebaseAuth    firebaseAuth instance.
     * @param idTokenListener given token listener.
     * @return a {@link Completable} which emits when the action is completed.
     */
    @NonNull
    public static Completable addIdTokenListener(@NonNull final FirebaseAuth firebaseAuth,
                                                 @NonNull final FirebaseAuth.IdTokenListener idTokenListener) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                firebaseAuth.addIdTokenListener(idTokenListener);
                emitter.onComplete();
            }
        });
    }

    /**
     * Unregisters a listener of ID token changes.
     * Listener must previously been added with AddIdTokenListener.
     *
     * @param firebaseAuth    firebaseAuth instance.
     * @param idTokenListener given token listener.
     * @return a {@link Completable} which emits when the action is completed.
     */
    @NonNull
    public static Completable removeIdTokenListener(@NonNull final FirebaseAuth firebaseAuth,
                                                    @NonNull final FirebaseAuth.IdTokenListener idTokenListener) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                firebaseAuth.removeIdTokenListener(idTokenListener);
                emitter.onComplete();
            }
        });
    }
}