package com.audioquiz.data.remote.service.auth;


import com.audioquiz.api.util.AuthorizationGateway;
import com.audioquiz.data.remote.datasource.AuthDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthorizationGatewayImpl implements AuthorizationGateway {

     private final AuthDataSource dataSource;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Inject
    public AuthorizationGatewayImpl(AuthDataSource dataSource) {
         this.dataSource = dataSource;
    }

    @Override
    public Observable<Boolean> isAuthorized() {
        return Observable.fromCallable(() -> dataSource.getCurrentUser() != null)
                .subscribeOn(Schedulers.from(executor));
    }

    @Override
    public Observable<Void> logOut() {
        return Observable.fromCallable(() -> {
            dataSource.logOut();
            return (Void) null;
        }).subscribeOn(Schedulers.from(executor));
    }

}
