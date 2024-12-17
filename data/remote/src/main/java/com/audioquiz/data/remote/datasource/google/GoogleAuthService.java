package com.audioquiz.data.remote.datasource.google;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.FragmentActivity;

import com.audioquiz.api.datasources.google.GoogleApi;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoogleAuthService implements GoogleApi {
    private static final String TAG = "GoogleAuthService";

    private final AuthProvider authProvider;
    private final GetGoogleIdOption googleIdOption;
    private final CredentialManager credentialManager;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();


    @Inject
    public GoogleAuthService(
            AuthProvider authProvider,
            GetGoogleIdOption googleIdOption,
            CredentialManager credentialManager) {
        this.authProvider = authProvider;
        this.googleIdOption = googleIdOption;
        this.credentialManager = credentialManager;
    }


    private FirebaseAuth getAuth() {
        return authProvider.getAuth();
    }


    @Override
    public void signIn(FragmentActivity activity, GoogleApi.GoogleSignInCallback callback) {
        try {
            getCredentialAsync(activity, callback);
        } catch (Exception e) {
            Log.e(TAG, "Error getting credential", e);
            callback.onSignInError(e);
        }
    }

    private void getCredentialAsync(FragmentActivity activity, GoogleApi.GoogleSignInCallback callback) {
        credentialManager.getCredentialAsync(activity, getCredentialRequest(), null, executor,
                new CredentialManagerCallback<>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleCredentialResult(result, callback);
                    }

                    @Override
                    public void onError(@NonNull GetCredentialException e) {
                        Log.e(TAG, "Error getting credential", e);
                        callback.onSignInError(e);
                    }
                }
        );
    }

    private GetCredentialRequest getCredentialRequest() {
        return new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();
    }

    private void handleCredentialResult(GetCredentialResponse result, GoogleApi.GoogleSignInCallback callback) {
        Credential credential = result.getCredential();
        if (credential.getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
            try {
                GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.getData());
                callback.onSignInSuccess(googleIdTokenCredential.getIdToken());
            } catch (Exception e) {
                Log.e(TAG, "Received an invalid google id token response", e);
                callback.onSignInError(e);
            }
        } else {
            Log.e(TAG, "Unexpected type of credential");
            callback.onSignInError(new Exception("Unexpected credential type"));
        }
    }


    /*public Task<Boolean> verifyGoogleSignIn() {
        FirebaseUser user = getAuth().getCurrentUser();
        if (user == null || user.getProviderData().stream().noneMatch(info -> "google.com".equals(info.getProviderId()))) {
            return Tasks.forResult(false);
        }

        TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();
        Task<GetCredentialResponse> getCredentialTask = credentialManager.getCredential(context, getCredentialRequest(), null, CancellationSignal.class.getI, executor, null);
        return getCredentialTask.continueWith(task -> {
            if (task.isSuccessful()) {
                return isValidGoogleCredential(task.getResult().getCredential());
            } else {
                Log.e(TAG, "Error getting credential", task.getException());
                return false;
            }
        });
    }*/

    private boolean isValidGoogleCredential(Credential credential) {
        if (GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
            try {
                GoogleIdTokenCredential.createFrom(credential.getData());
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Invalid google id token response", e);
            }
        } else {
            Log.e(TAG, "Unexpected credential type");
        }
        return false;
    }

    private Task<Void> signOut() {
        getAuth().signOut();  // Sign out the user
        return Tasks.forResult(null);
    }

}
