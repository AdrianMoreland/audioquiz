package com.audioquiz.data.remote.di;


import android.content.Context;

import androidx.credentials.CredentialManager;

import com.audioquiz.api.datasources.google.GoogleApi;
import com.audioquiz.data.remote.BuildConfig;
import com.audioquiz.data.remote.datasource.google.GoogleAuthService;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class GoogleAuthModule {

    @Singleton
    @Provides
    public GetGoogleIdOption provideGetGoogleIdOption() {
        return new GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId("719684506689-r400lat3kk6gauttcb0sksleogenh9e6.apps.googleusercontent.com")
                .build();
    }


    @Provides
    @Singleton
    @Named("webClientId")
    String provideWebClientId() {
        return "719684506689-r400lat3kk6gauttcb0sksleogenh9e6.apps.googleusercontent.com";
    }

    @Singleton
    @Provides
    public CredentialManager provideCredentialManager(@ApplicationContext Context context) {
        return CredentialManager.create(context);
    }

    @Provides
    public GoogleApi provideAuthService(
            AuthProvider authProvider,
            GetGoogleIdOption googleIdOption,
            CredentialManager credentialManager) {
        return new GoogleAuthService(authProvider, googleIdOption, credentialManager);
    }
}