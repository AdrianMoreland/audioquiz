package com.audioquiz.data.remote.datasource.auth;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.PasswordCredential;
import androidx.credentials.PublicKeyCredential;
import androidx.credentials.exceptions.GetCredentialException;
import androidx.fragment.app.FragmentActivity;

import com.audioquiz.api.util.GoogleSignInHelper;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;

@Singleton
public class GoogleSignInDataSource implements GoogleSignInHelper {
    private static final String TAG = "GoogleSignInDataSource";
    private final AuthProvider authProvider;

    private final String webClientId;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Inject
    public GoogleSignInDataSource(@Named("webClientId") String webClientId,
                                  AuthProvider authProvider) {
        this.webClientId = webClientId;
        this.authProvider = authProvider;
    }

    public FirebaseAuth getAuth() {
        return authProvider.getAuth();
    }

    public Task<AuthResult> signInWithGoogle(String idToken) {
        Log.d(TAG, "Auth with Google called: " + idToken);
        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
        return getAuth().signInWithCredential(firebaseCredential);
    }

    @Override
    public Observable<String> fetchToken(Context appContext, String webClientId) {
        return Observable.create(emitter -> {
            // Get the CredentialManager instance
            CredentialManager credentialManager = CredentialManager.create(appContext.getApplicationContext());

            try {
                credentialManager.getCredentialAsync(appContext.getApplicationContext(), getCredentialRequest(webClientId), null, executor,
                        new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                            @Override
                            public void onResult(GetCredentialResponse result) {
                                Credential credential = result.getCredential();
                                if ((credential).getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                                    try {
                                        GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom((credential).getData());
                                        String idToken = googleIdTokenCredential.getIdToken();
                                        emitter.onNext(idToken); // Emit the token
                                        emitter.onComplete(); // Signal completion
                                    } catch (Exception e) {
                                        Log.e(TAG, "Received an invalid google id token response", e);
                                        emitter.onError(e); // Emit the error
                                    }
                                } else {
                                    // Catch any unrecognized custom credential type here.
                                    Log.e(TAG, "Unexpected type of credential");
                                    emitter.onError(new Exception("Unexpected credential type")); // Emit the error
                                }
                            }

                            @Override
                            public void onError(@NonNull GetCredentialException e) {
                                Log.e(TAG, "Error getting credential", e);
                                emitter.onError(e); // Emit the error
                            }
                        }
                );
            } catch (Exception e) {
                Log.e(TAG, "Error getting credential2", e);
                emitter.onError(e); // Emit the error
            }
        });
    }

    private GetCredentialRequest getCredentialRequest(String webClientId) {
        return new GetCredentialRequest.Builder()
                .addCredentialOption(getGoogleIdTokenOption(webClientId))
                .build();
    }

    private GetGoogleIdOption getGoogleIdTokenOption(String webClientId) {
        return new GetGoogleIdOption.Builder()
                .setServerClientId(webClientId)
                .setFilterByAuthorizedAccounts(true) // Only show accounts previously used to sign in
                .setAutoSelectEnabled(true) // Automatically sign in when exactly one credential is retrieved
                .build();
    }

    @Override
    public void signIn(FragmentActivity activity, GoogleSignInCallback callback) {
        Log.d(TAG, "Credentials API: signIn " + activity.getPackageName() );
        CredentialManager credentialManager = CredentialManager.create(activity);

        executor.execute(() -> {
            try {
                credentialManager.getCredentialAsync(activity, getCredentialRequest2(), null, executor,
                        new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                            @Override
                            public void onResult(GetCredentialResponse result) {
                                handleSignIn(result, callback);
                            }

                            @Override
                            public void onError(@NonNull GetCredentialException e) {
                                Log.e(TAG, "Error getting credential");
                                handleFailure(e, callback);
                            }
                        }
                );
            } catch (Exception e) {
                Log.e(TAG, "Error getting credential2");
                handleFailure((GetCredentialException) e, callback);
            }
        });
    }

    private GetCredentialRequest getCredentialRequest2() {
        return new GetCredentialRequest.Builder()
                .addCredentialOption(getGoogleIdOption())
                .build();
    }

    private GetGoogleIdOption getGoogleIdOption() {
        return new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(true)
                .setNonce(generateNonce())
                .build();
    }

    public void handleSignIn(GetCredentialResponse result, GoogleSignInCallback callback) {
        // Handle the successfully returned credential.
        Credential credential = result.getCredential();
        if (credential instanceof PasswordCredential) {
            String username = ((PasswordCredential) credential).getId();
            String password = ((PasswordCredential) credential).getPassword();
            // Use id and password to send to your server to validate and authenticate
        }
        else if (credential instanceof CustomCredential) {
            if ((credential).getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                try {
                    GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom((credential).getData());
                    callback.onSignInSuccess(googleIdTokenCredential.getIdToken());
                } catch (Exception e) {
                    callback.onSignInError(e);
                    Log.e(TAG, "Received an invalid google id token response", e);
                }
            } else {
                // Catch any unrecognized custom credential type here.
                Log.e(TAG, "Unexpected type of credential");
            }
        }
        else {
            // Catch any unrecognized credential type here.
            Log.e(TAG, "Unexpected type of credential");
        }
    }

    private void handleFailure(GetCredentialException e, GoogleSignInCallback callback) {
        Log.e(TAG, "Error getting credential", e);
    }

    private String generateNonce() {
        return null;
    }


}
