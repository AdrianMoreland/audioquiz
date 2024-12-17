/*
package com.audioquiz.sync.worker.status;

import com.audioquiz.sync.worker.util.ChangeListVersions;

import io.reactivex.rxjava3.core.Single;

public interface Synchronizer {
    Single<ChangeListVersions> getChangeListVersions();

    Single<ChangeListVersions> updateChangeListVersions(ChangeListVersions update);

    // Helper method to sync a Syncable object
    default Single<Boolean> sync(Syncable syncable) {
        return syncable.syncWith(this);
    }
}
*/
