package com.audioquiz.data.repository.stats;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.audioquiz.api.datasources.user.UserProfileDataSource;
import com.audioquiz.core.domain.repository.user.UserProfileRepository;
import com.audioquiz.core.model.user.UserProfile;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserProfileRepositoryImpl implements UserProfileRepository {
    private static final String TAG = "UserProfileRepositoryImpl";
     private final MutableLiveData<UserProfile> userProfileLiveData = new MutableLiveData<>();
    private final UserProfileDataSource.Local local;
    private final UserProfileDataSource.Remote remote;

    @Inject
    public UserProfileRepositoryImpl(UserProfileDataSource.Local local, UserProfileDataSource.Remote remote) {
        this.local = local;
        this.remote = remote;
    }
    public Completable init() {
        return getUserProfileSingle()
                .flatMapCompletable(userProfile -> {
                    if (userProfile != null) {
                        userProfileLiveData.postValue(userProfile);
                        Log.d(TAG, "User profile fetched from cache");
                        return saveUserProfile(userProfile)
                                .doOnComplete(() -> {
                                    // Fetch from Firestore and update cache
//                                    checkUserProfileDocumentExistence();
                                    Log.d(TAG, "UserProfileRepositoryImpl initialized");
                                });
                    } else {
                        Log.d(TAG, "User profile not found in cache");
                        // Emit an error or complete with a failure
                        return Completable.error(new Exception("User profile not found"));
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    // Public method to get UserProfile
    public Single<UserProfile> getUserProfile() {
        Log.d(TAG, "getUserProfile called");

        // First, try to get local user profile
        return local.getUserProfileSingle()
                .flatMap(localProfile -> {
                    Log.d(TAG, "Local user profile found");

                    // Optionally, fetch remote data if local data is present
                    return remote.getUserProfile()
                            .flatMap(remoteProfile -> {
                                // Use getLatestUserProfile to determine which profile to keep
                                UserProfile latestProfile = getLatestUserProfile(localProfile, remoteProfile);

                                // Check if the latest profile is the remote one and save if it is newer
                                if (latestProfile.equals(remoteProfile)) {
                                    Log.d(TAG, "Remote data is newer, updating local storage");
                                    return saveUserProfileLocally(remoteProfile)
                                            .toSingleDefault(remoteProfile);
                                }
                                Log.d(TAG, "Local data is up-to-date, returning local profile");
                                return Single.just(localProfile);
                            })
                            .onErrorResumeNext(throwable -> {
                                Log.e(TAG, "Error fetching remote profile, using local data", throwable);
                                // In case of error fetching remote data, return the local profile
                                return Single.just(localProfile);
                            });
                })
                .onErrorResumeNext(error -> {
                    Log.d(TAG, "No local profile found, fetching from remote", error);
                    // If local profile is unavailable, attempt to fetch from remote
                    return remote.getUserProfile()
                            .flatMap(remoteProfile -> {
                                // Save the remote profile locally for future use
                                return saveUserProfileLocally(remoteProfile)
                                        .toSingleDefault(remoteProfile);
                            })
                            .onErrorReturnItem(UserProfile.createDefault(getUserId(), getUsername())); // Return default if remote also fails
                });
    }

    @Override
    public Single<UserProfile> getUserProfileSingle() {
        return local.getUserProfileSingle()
                .onErrorResumeNext(error ->
                        remote.getUserProfile()
                                .flatMap(userProfile ->
                                        saveUserProfileLocally(userProfile)
                                                .toSingleDefault(userProfile))
                );
    }

    // Method to sync data between Firestore and Room
    public Single<Boolean> sync() {
        return remote.getUserProfile()
                .flatMap(remoteStats -> local.getUserProfileSingle()
                        .flatMap(localStats -> {
                            UserProfile latestStats = getLatestUserProfile(localStats, remoteStats);
                            if (latestStats != null) {
                                return saveUserProfileLocally(latestStats)
                                        .andThen(Single.just(true)); // Sync successful
                            }
                            return Single.just(false); // No data to sync
                        })
                )
                .onErrorReturn(throwable -> {
                    Log.e(TAG, "Sync error: ", throwable);
                    return false; // Return false on error
                });
    }

    // Method to get the latest UserProfile based on freshness
    private UserProfile getLatestUserProfile(UserProfile local, UserProfile remote) {
        if (local == null) return remote; // No local data, return remote
        if (remote == null) return local; // No remote data, return local
        return local.getLastUpdated() >= remote.getLastUpdated() ? local : remote;
    }



    private LiveData<UserProfile> getUserProfileLiveData() {
        return userProfileLiveData;
    }

    //region CACHE_METHODS

    public Completable saveUserProfile(UserProfile userProfile) {
        return Completable.fromAction(() -> {
            userProfile.setLastUpdated(System.currentTimeMillis());
            local.storeUserProfileInfo(userProfile);
            userProfileLiveData.postValue(userProfile);
            // Update network asynchronously on Firestore
            remote.updateUserProfileDocument(getUserId(), userProfile);
        }).subscribeOn(Schedulers.io());
    }


    public void flushUserProfileData() {
        local.flushData();
    }
   //endregion

    //region USER_PROFILE_DATA_GETTERS

    private Completable saveUserProfileLocally(UserProfile userProfile) {
        return  local.insert(userProfile);
    }

    private String getUserId() {
        UserProfile profile = getUserProfileLiveData().getValue();return profile != null ? profile.getUserId() : "";
    }

    private String getUsername() {
        UserProfile profile = getUserProfileLiveData().getValue();
        return profile != null ? profile.getUsername() : "";
    }



    @Override
    public String getUsernameLetter() {
        String firstLetter = String.valueOf('?');
        if (getUserProfileLiveData().getValue() != null) {
            String username = getUserProfileLiveData().getValue().getUsername();
            firstLetter = String.valueOf(Character.toUpperCase(username.charAt(0)));
            Log.d(TAG, "getUsernameLetter: " + firstLetter);
        }
        return firstLetter;
    }
    //endregion

    //region USER_PROFILE_UPDATES
    @Override
    public void updateUsername(String username, UsernameCompletionCallback completionCallback) {
        UserProfile userProfile = getUserProfileLiveData().getValue();
        if (userProfile != null) {
            userProfile.setUsername(username);
            saveUserProfile(userProfile);
        }
    }

    @Override
    public void uploadProfileImage(String imageUri, ImageUploadCallback listener) {
        // TODO: Implement image upload
    }
    //endregion

    //region FIRESTORE
/*
    private void checkUserProfileDocumentExistence() {
        Single<Optional<UserProfile>> networkProfileSingle = api.getUserProfile()
                .map(Optional::ofNullable);
        Single<Optional<UserProfile>> cachedProfileSingle = cache.getUserProfileSingle()
                .map(Optional::ofNullable);

        Single.zip(networkProfileSingle, cachedProfileSingle, this::getLatestUserProfile)
                .flatMap(optionalProfile -> {
                    if (optionalProfile.isPresent()) {
                        UserProfile profile = optionalProfile.get();
                        return Single.fromCallable(() -> {
                            api.saveUserProfile(profile);
                            cache.insert(profile);
                            return profile;
                        });
                    } else {
                        return Single.error(new ProfileNotFoundException("No user profile found."));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userProfile -> setupUserProfileListener(), error -> {
                    if (error instanceof ProfileNotFoundException) {
                        // Handle case where no profile is found (e.g., show message to user)
                    } else {
                        // Handle other errors (e.g., network or database errors)
                    }
                    Log.e("ProfileError", "Error fetching user profile", error);
                });
    }


    private Optional<UserProfile> getNewestProfile(Optional<UserProfile> networkProfile, Optional<UserProfile> cachedProfile) {
        if (networkProfile.isPresent()) {
            if (cachedProfile.isPresent() && cachedProfile.get().getLastUpdated() > networkProfile.get().getLastUpdated()) {
                return cachedProfile;
            }
            return networkProfile;
        }
        return cachedProfile;
    }
*/
    public static class ProfileNotFoundException extends Exception {
        public ProfileNotFoundException(String message) {
            super(message);
        }
    }

    // CREATE DOCUMENT IN FIRESTORE
    private void createUserProfileDocument() {
        UserProfile userProfile = UserProfile.createDefault("","");
        // Step 2: Save the UserProfile to Firestore
        remote.saveUserProfile(userProfile)
                .subscribeOn(Schedulers.io())  // Step 3: Perform the operation on the IO thread
                .observeOn(AndroidSchedulers.mainThread())  // Step 4: Observe the result on the main thread
                .subscribe(() -> {
                    // Step 5: Handle the success case
                    Log.d("Firestore", "UserProfile document successfully created!");
                }, throwable -> {
                    // Step 6: Handle the failure case
                    Log.e("Firestore", "Error creating UserProfile document", throwable);
                });

    }

    // UPDATE USER PROFILE IN FIRESTORE

    private void saveUserProfileRemotely(UserProfile userProfile) {
        userProfile.setLastUpdated(System.currentTimeMillis());
        remote.updateUserProfileDocument(getUserId(), userProfile)
                .addOnSuccessListener(aVoid -> {
                    saveUserProfile(userProfile);
                    Log.d(TAG, "User profile updated successfully");
                })
                .addOnFailureListener(e -> {
                    // Update failed
                    Log.e("UserProfile", "Failed to update user profile", e);
                });
    }

    // Update the user profile image URL in Firestore
    private void updateProfileImageInFirestore(String downloadUrl) {
        // Get the cached Userprofile object
        if (getUserProfileLiveData().getValue() != null){
            getUserProfileLiveData().getValue().setProfileImage(downloadUrl);
            // Update the Firestore document with the new Userprofile object
            saveUserProfileRemotely(getUserProfileLiveData().getValue());
        }

    }
    //endregion

}
