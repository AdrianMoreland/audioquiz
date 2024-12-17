package com.audioquiz.data.remote.datasource;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.audioquiz.core.model.auth.LoggedInUser;
import com.audioquiz.core.model.util.Result;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import javax.inject.Inject;

public class AuthDataSource {
    private static final String TAG = "AuthDataSource";
     private final AuthProvider authProvider;
    private final FirestoreProvider firestoreProvider;
    private final MutableLiveData<String> registrationErrorMessage = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();

    @Inject
    public AuthDataSource(AuthProvider authProvider,
                          FirestoreProvider firestoreProvider) {
        this.authProvider = authProvider;
        this.firestoreProvider = firestoreProvider;
    }

    public FirebaseAuth getAuth() {
        return authProvider.getAuth();
    }

    public FirebaseUser getCurrentUser() {
        return getAuth().getCurrentUser();
    }

    public String getFirebaseUserId() {
        FirebaseUser user = getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    public LoggedInUser getLoggedInUser() {
        FirebaseUser firebaseUser = getCurrentUser();
        LoggedInUser loggedInUser;
        if (firebaseUser != null) {
            return new Result.Success<>(new LoggedInUser(
                    System.currentTimeMillis(),
                    firebaseUser.getUid(),
                    firebaseUser.getDisplayName(),
                    firebaseUser.getEmail())).getData();
        } else {
            Log.e(TAG, "User not found");
            loggedInUser = new LoggedInUser(
                    System.currentTimeMillis(),
                    new Result.Error(new Exception("User not found")).toString(),
                    new Exception("User not found").toString(),"");
            return loggedInUser;
        }
    }

    public Task<AuthResult> signInWithEmailAndPassword(String email, String password) {
        return getAuth().signInWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signInWithCredential(String idToken) {
        Log.d(TAG, "Auth with Google called: " + idToken);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        return getAuth().signInWithCredential(credential);
    }

    public Task<AuthResult> signUp(String email, String password) {
        return getAuth().createUserWithEmailAndPassword(email, password);
    }

    public Task<Void> sendVerificationEmail() {
        FirebaseUser user = authProvider.getAuth().getCurrentUser();
        if (user != null) {
            return user.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    });
        } else {
            return Tasks.forException(new Exception("User not logged in"));
        }
    }

    public Task<Void> sendPasswordResetEmail(String email) {
        return authProvider.getAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                    }
                });
    }

    public void handleRegistrationError(Exception exception) {
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
        FirebaseUser user = authProvider.getAuth().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            return user.reauthenticate(credential).continueWithTask(task -> {
                if (task.isSuccessful()) {
                    return user.updatePassword(newPassword);
                } else {
                    throw Objects.requireNonNull(task.getException());
                }
            });
        } else {
            return Tasks.forException(new Exception("User not authenticated"));
        }
    }

    public void deleteUserAccount() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    });
        }
    }

    public void logOut() {
        authProvider.getAuth().signOut();
    }


    public void getUserProfile() {
        FirebaseUser user = authProvider.getAuth().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            }
        }
    }

    public void updateUserProfile() {
        FirebaseUser user = authProvider.getAuth().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(" User")
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        Objects.requireNonNull(user).updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });
    }

    public void updateEmail() {
        FirebaseUser user = authProvider.getAuth().getCurrentUser();

        if (user != null) {
            user.verifyBeforeUpdateEmail("user@example.com")
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    });
        }
    }

    public void updatePassword() {
        FirebaseUser user = authProvider.getAuth().getCurrentUser();

        if (user != null) {
            user.updatePassword("newPassword123")
                    .addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User password updated.");
                                }
                            });
        }
    }

    public void reauthenticate() {
        FirebaseUser user = authProvider.getAuth().getCurrentUser();

// Prompt the user to re-provide their sign-in credentials
        if (user != null) {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), "password1234");
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> Log.d(TAG, "User re-authenticated."));
        }
    }


/*

    private Task<AuthResult> authenticate(AuthCredential credential) {
        return getAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User authenticated successfully.");
                        FirebaseUser user = getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "User logged in with email and password.");
                        }
                    } else {
                        Log.e(TAG, "Failed to login with email and password.");
                        handleRegistrationError(task.getException());
                    }
                });
    }

    public Task<AuthResult> loginWithEmailAndPassword(String email, String password) {
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        return authenticate(credential);
    }

    public Task<AuthResult> createUserWithEmailAndPassword(String email, String password) {
        return getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sendVerificationEmail();
                    } else {
                        handleRegistrationError(task.getException());
                    }
                });
    }
*/


}
