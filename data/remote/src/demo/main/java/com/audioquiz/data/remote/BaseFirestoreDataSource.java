package com.audioquiz.data.remote.datasource;

import android.util.Log;

import com.audioquiz.data.remote.provider.AuthProvider;
import com.audioquiz.data.remote.provider.FirestoreProvider;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseFirestoreDataSource {
    protected final FirestoreProvider firestoreProvider;
    protected AuthProvider authProvider;
    protected FirebaseFirestore mFirestore;
    protected abstract CollectionReference getCollection();


    public BaseFirestoreDataSource(FirestoreProvider firestoreProvider,
                                   AuthProvider authProvider) {
        this.firestoreProvider = firestoreProvider;
        this.authProvider = authProvider;
    }

    protected FirebaseFirestore getFirestore() {
        if (mFirestore == null) {
            mFirestore = firestoreProvider.getFirestore();
        }
        Log.d("BaseFirestoreDataSource", "getFirestore: " + mFirestore);
        return mFirestore;
    }

    protected FirebaseUser getCurrentUser() {
        FirebaseAuth auth = authProvider.getAuth();
        if (auth != null) {
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                Log.d("BaseFirestoreDataSource", "getCurrentUser: " + user);
                return user;
            }
        }
        return null;
    }

    protected String getFirebaseUserId() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            Log.d("BaseFirestoreDataSource", "getFirebaseUserId: " + user.getUid());
            return user.getUid();
        }
        return null;
    }

    protected String getFirebaseUsername() {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return null;
    }



    // Create a new document with a given ID and data
    public void setDocument(String documentId, Object data) {
        getCollection().document(documentId).set(data);
    }

    // Read a document with a given ID
    public Task<DocumentSnapshot> getDocument (String documentId) {
        return getCollection().document(documentId).get();
    }

    // Update a document with a given ID and data
    public Task<Void> updateDocument (String id, Map<String, Object> data) {
        return getCollection()
                .document(id)
                .update(data);
    }

    // Delete a document with a given ID
    public Task<Void> deleteDocument (String documentId) {
        return getCollection().document(documentId).delete();
    }

        public void createOrUpdate(Map<String, Object> models) {
            runBatch(models.entrySet(), (batch, collection, entry) -> {
                batch.set(collection.document(entry.getKey()), entry.getValue());
            });
        }

        public void delete(Collection<String> ids) {
            runBatch(ids, (batch, collection, id) -> {
                batch.delete(collection.document(id));
            });
        }

    public Query getQuery(int limit, String field, Query.Direction direction) {
        return getCollection()
                .orderBy(field, direction)
                .limit(limit);
    }

        private <T> void runBatch(Collection<T> models, BatchOperation<T> operation) {
            CollectionReference collection = getCollection();
            List<T> modelList = new ArrayList<>(models);
            int batchSize = 499;
            for (int i = 0; i < modelList.size(); i += batchSize) {
                int end = Math.min(i + batchSize, modelList.size());
                List<T> batchModels = modelList.subList(i, end);
                WriteBatch batch = mFirestore.batch();
                for (T model : batchModels) {
                    operation.execute(batch, collection, model);
                }
                batch.commit();
            }
        }
    @FunctionalInterface
        interface BatchOperation<T> {
            void execute(WriteBatch batch, CollectionReference collection, T model);
        }


}