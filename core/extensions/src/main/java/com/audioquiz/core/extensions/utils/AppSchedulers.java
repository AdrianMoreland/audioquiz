package com.audioquiz.core.extensions.utils;


import io.reactivex.rxjava3.core.Scheduler;

public class AppSchedulers {
    private final Scheduler mainThread;
    private final Scheduler io;
    private final Scheduler computation;

    public AppSchedulers(Scheduler mainThread, Scheduler io, Scheduler computation) {
        this.mainThread = mainThread;
        this.io = io;
        this.computation = computation;
    }

    public Scheduler getMainThread() {
        return mainThread;
    }

    public Scheduler getIo() {
        return io;
    }

    public Scheduler getComputation() {
        return computation;
    }
}
