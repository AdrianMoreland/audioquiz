package com.audioquiz.data.remote.di;

import android.app.Application;
import android.content.Context;

import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.audioquiz.core.extensions.utils.AppConstants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class FirebaseModule {

    @Provides
    @Singleton
    static FirebaseApp provideFirebaseApp(Application application) {
        return FirebaseApp.initializeApp(application);
    }

    @Provides
    @Singleton
    static FirestoreProvider provideFirestoreProvider() {
        return new FirestoreProvider();
    }

    @Provides
    @Singleton
    static AuthProvider provideAuthProvider() {
        return new AuthProvider();
    }

    @Provides
    public static FirebaseAnalytics provideFirebaseAnalytics(@ApplicationContext Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

    @Provides
    @Singleton
    FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Provides
    @Singleton
    @Named("webClientId")
    String provideWebClientId() {
        return AppConstants.WEB_CLIENT_ID;
    }

}