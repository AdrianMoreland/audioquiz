package com.audioquiz.data.remote.datasource;

import android.util.Log;

import com.audioquiz.api.exceptions.DataNotFoundTypeException;
import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.audioquiz.data.remote.util.mapper.NetworkMapper;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.PersistentCacheSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirestoreDataSource<T> {
    private static final String TAG = "FirestoreDataSource";
    private final FirestoreProvider firestoreProvider;
    private final AuthProvider authProvider;
    private String firebaseUserId;

    @Inject
    public FirestoreDataSource(FirestoreProvider firestoreProvider,
                               AuthProvider authProvider) {
        this.firestoreProvider = firestoreProvider;
        this.authProvider = authProvider;
        setupFirestore();
    }

    public void setupFirestore() {
        FirebaseFirestoreSettings settings =
                new FirebaseFirestoreSettings.Builder(firestoreProvider.getFirestore().getFirestoreSettings())
                        // Use memory-only cache
                        .setLocalCacheSettings(MemoryCacheSettings.newBuilder().build())
                        // Use persistent disk cache (default)
                        .setLocalCacheSettings(PersistentCacheSettings.newBuilder().build())
                        .build();
        firestoreProvider.getFirestore().setFirestoreSettings(settings);
    }

    public String getFirebaseUserId() {
        if (firebaseUserId == null) {
            FirebaseUser user = authProvider.getAuth().getCurrentUser();
            if (user != null) {
                firebaseUserId = user.getUid();
            }
        }
        return firebaseUserId;
    }

    //region Private utility methods

    private CollectionReference getRootCollection(String collectionName) {
        return firestoreProvider.getFirestore().collection(collectionName);
    }

    private DocumentReference getDocumentReference(String collectionName) {
        if (getFirebaseUserId() == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        return getRootCollection(collectionName).document(getFirebaseUserId());
    }

    private CollectionReference getSubCollection(String subCollectionName) {
        return getDocumentReference(getFirebaseUserId()).collection(subCollectionName);
    }

    private DocumentReference getSubDocument(String collectionName, String subCollectionName) {
        Log.d(TAG, "getSubDocument: " + collectionName + ", " + subCollectionName);
        return getSubCollection(subCollectionName).document(getFirebaseUserId());
    }

    //endregion

    //region CRUD Operations
    public Task<DocumentSnapshot> getDocument(String collectionName) {
        Log.d(TAG, "getDocumentSnapshot: " + collectionName);
        return getDocumentReference(collectionName)
                .get()
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        Log.d(TAG, "Data fetched from " + (documentSnapshot.getMetadata().isFromCache() ? "local cache" : "server"));
                        return Tasks.forResult(documentSnapshot);
                    } else {
                        // Handle the error
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseFirestoreException && ((FirebaseFirestoreException) exception).getCode() == FirebaseFirestoreException.Code.NOT_FOUND) {
                            return Tasks.forException(new DataNotFoundTypeException("Document not found", getFirebaseUserId()));
                        } else {
                            // Other error, re-throw
                            return Tasks.forException(exception);
                        }
                    }
                });
    }

    public Task<Void> setDocument(String collectionName, T data) {
        Log.d(TAG, "setDocument: " + data);
        return getDocumentReference(collectionName)
                .set(toMap(data));
    }

    public Task<Void> updateDocument(String collectionName, T data) {
        Log.d(TAG, "updateDocument: " + data);
        return getSubDocument(collectionName, getFirebaseUserId())
                .update(toMap(data));
    }

    public Task<Void> deleteDocument(String collectionName) {
        Log.d(TAG, "deleteDocument: " + collectionName);
        return getDocumentReference(collectionName).delete();
    }

    private void getGroup(String subCollectionName, String orderBy) {
        Log.d(TAG, "getSubDocument: " + ", " + subCollectionName);
        firestoreProvider.getFirestore().collectionGroup(subCollectionName).orderBy(orderBy)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public List<T> getItems(String collectionName, Class<T> clazz) {
        try {
            return Tasks.await(getRootCollection(collectionName).get())
                    .getDocuments()
                    .stream()
                    .map(document -> document != null ? document.toObject(clazz) : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public Task<Void> updateSubDocumentMap(String collectionName, String documentName, String key, T data) {
        Log.d(TAG, "updateSubDocumentMap: " + data);
        Map<String, Object> map = new HashMap<>();
        map.put(key, data);
        return getSubDocument(collectionName, documentName).update(map);
    }

    public Query getQuery(String collectionName, String subCollectionName, int limit, String field, Query.Direction direction) {
        return getSubCollection(subCollectionName)
                .orderBy(field, direction)
                .limit(limit);
    }

    //endregion


    @SuppressWarnings("unchecked")
    private Map<String, Object> toMap(T data) {
        return NetworkMapper.map(data, HashMap.class);
    }

    public Completable runBatch(String subCollectionName, Collection<T> models, BatchOperation<T> operation) {
        return Completable.create(emitter -> {
            CollectionReference collection = getSubCollection(subCollectionName);
            List<T> modelList = new ArrayList<>(models);
            int batchSize = 499;
            for (int i = 0; i < modelList.size(); i += batchSize) {
                int end = Math.min(i + batchSize, modelList.size());
                List<T> batchModels = modelList.subList(i, end);
                WriteBatch batch = firestoreProvider.getFirestore().batch();
                for (T model : batchModels) {
                    operation.execute(batch, collection, model, getFirebaseUserId());
                }
                batch.commit()
                        .addOnSuccessListener(aVoid -> {
                            // Handle success if needed
                        })
                        .addOnFailureListener(emitter::onError);
            }
            emitter.onComplete();
        }).subscribeOn(Schedulers.io());
    }


    @FunctionalInterface
    public interface BatchOperation<T> {
        void execute(WriteBatch batch, CollectionReference collection, T model, String documentId);
    }
}
