package com.audioquiz.sync.di;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
     @InstallIn(SingletonComponent.class)
     public interface HiltInitializationEntryPoint {
         // An empty interface to trigger Hilt initialization
     }