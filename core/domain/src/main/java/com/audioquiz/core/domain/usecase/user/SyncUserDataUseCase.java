package com.audioquiz.core.domain.usecase.user;

import io.reactivex.rxjava3.core.Completable;

public interface SyncUserDataUseCase {
    Completable execute();
}
