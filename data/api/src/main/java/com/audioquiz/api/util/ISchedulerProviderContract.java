package com.audioquiz.api.util;

import io.reactivex.rxjava3.core.Scheduler;

public interface ISchedulerProviderContract {
    Scheduler io();

    Scheduler ui();
}
