package com.audioquiz.sync.initializer

import android.content.Context
import androidx.startup.Initializer
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.WorkManagerInitializer
import com.audioquiz.sync.Sync
import com.audioquiz.sync.SyncWorkName
import com.audioquiz.sync.worker.SyncWorker

class SyncInitializer : Initializer<Sync> {
    override fun create(context: Context): Sync {
        WorkManager.getInstance(context).apply {
            // Run sync on app startup and ensure only one sync worker runs at any time
            enqueueUniqueWork(
                SyncWorkName,
                ExistingWorkPolicy.KEEP,
                SyncWorker.startUpSyncWork(),
            )
        }

        return Sync
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(WorkManagerInitializer::class.java)
}
