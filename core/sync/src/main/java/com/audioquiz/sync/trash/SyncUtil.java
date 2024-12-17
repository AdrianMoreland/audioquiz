/*
package com.audioquiz.sync.worker.util.trash;

import com.audioquiz.sync.worker.status.Synchronizer;
import com.audioquiz.sync.worker.util.ChangeListVersions;
import com.audioquiz.sync.worker.util.DataItem;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SyncUtil {

    public static Completable syncData(Synchronizer synchronizer, DataFetcher dataFetcher, DataUpdater dataUpdater) {
        return synchronizer.getChangeListVersions()
                .flatMap(changeListVersions -> {
                    // Fetch data from Firebase based on the current version
                    return dataFetcher.fetch()
                            .flatMap(fetchedData -> {
                                if (fetchedData.isEmpty()) {
                                    return Single.just(true); // No changes, no need to update
                                }

                                // Update the local database with new data
                                return dataUpdater.update(fetchedData)
                                        .andThen(Single.fromCallable(() -> {
                                            // Extract the latest version based on the data (you can change this logic)
                                            int latestVersion = getLatestVersionFrom(fetchedData);

                                            // Create a new ChangeListVersions object with the updated version
                                            ChangeListVersions updatedVersions = new ChangeListVersions(
                                                    latestVersion,  // Set the topicVersion
                                                    changeListVersions.getNewsResourceVersion()  // Keep the newsResourceVersion unchanged
                                            );

                                            // Now update the ChangeListVersions
                                            return synchronizer.updateChangeListVersions(updatedVersions)
                                                    .map(updated -> true);  // Sync successful
                                        }));
                            });
                })
                .ignoreElement(); // Convert Single<Boolean> to Completable
    }

    private static int getLatestVersionFrom(List<DataItem> data) {
        // Logic to extract the latest version from fetched data
        // Assuming DataItem has a method to get the version
        return Integer.parseInt(data.get(data.size() - 1).getVersion());
    }

    @FunctionalInterface
    public interface DataFetcher {
        Single<List<DataItem>> fetch();
    }

    @FunctionalInterface
    public interface DataUpdater {
        Completable update(List<DataItem> data);
    }
}*/
