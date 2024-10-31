package com.audioquiz.data.remote.datasource.auth;

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
    public void signIn(FragmentActivity activity, GoogleSignInCallback callback) {
        Log.d(TAG, "Credentials API: signIn " + activity.getPackageName() );
        CredentialManager credentialManager = CredentialManager.create(activity);
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(true)
                .setNonce(generateNonce())
                .build();

        GetCredentialRequest credentialRequest = new GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build();

        executor.execute(() -> {
            try {
                credentialManager.getCredentialAsync(activity, credentialRequest, null, executor,
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

    public void handleSignIn(GetCredentialResponse result, GoogleSignInCallback callback) {
        // Handle the successfully returned credential.
        Credential credential = result.getCredential();
        if (credential instanceof PublicKeyCredential) {
            String responseJson = ((PublicKeyCredential) credential).getAuthenticationResponseJson();
            // Share responseJson i.e. a GetCredentialResponse on your server to validate and authenticate
        } else if (credential instanceof PasswordCredential) {
            String username = ((PasswordCredential) credential).getId();
            String password = ((PasswordCredential) credential).getPassword();
            // Use id and password to send to your server to validate and authenticate
        }
        else if (credential instanceof CustomCredential) {
            if ((credential).getType().equals(GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)) {
                try {
                    GoogleIdTokenCredential googleIdTokenCredential = GoogleIdTokenCredential.createFrom((credential).getData());
                    callback.onSignInSuccess(googleIdTokenCredential.getIdToken());
                    // Send googleIdTokenCredential to your server for validation and authentication
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
    /*
    public void firebaseAuthWithGoogle(String idToken) {
        GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(true)
                .setServerClientId(webClientId)
                .setAutoSelectEnabled(true)
                .setNonce("<nonce string to use when generating a Google ID token>").build();

    }


    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(CLIENT_ID))
            // Or, if multiple clients access the backend:
            //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
            .build();

// (Receive idTokenString by HTTPS POST)

    GoogleIdToken idToken = verifier.verify(idTokenString);
if (idToken != null) {
        Payload payload = idToken.getPayload();

        // Print user identifier
        String userId = payload.getSubject();
        System.out.println("User ID: " + userId);

        // Get profile information from payload
        String email = payload.getEmail();
        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");
        String locale = (String) payload.get("locale");
        String familyName = (String) payload.get("family_name");
        String givenName = (String) payload.get("given_name");

        // Use or store profile information
        // ...

    } else {
        System.out.println("Invalid ID token.");
    }

*/

}
