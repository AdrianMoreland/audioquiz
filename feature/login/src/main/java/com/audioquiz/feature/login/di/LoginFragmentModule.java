package com.audioquiz.feature.login.di;


import com.audioquiz.feature.login.sign_in.LoginFragment;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@Module
@InstallIn(FragmentComponent.class) // Or FragmentComponent
public class LoginFragmentModule {

    @Provides
    @FragmentScoped
    LoginFragment provideLoginFragment() {
        return new LoginFragment();
    }
}
