package com.audioquiz.api.util;

import io.reactivex.rxjava3.core.Observable;

public interface AuthorizationGateway {

    Observable<Boolean> isAuthorized();

    Observable<Void> logOut();

 }
