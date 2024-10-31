package com.audioquiz.admin.data;


import com.audioquiz.core.model.rank.RankEntry;
import com.audioquiz.core.model.user.UserProfile;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import timber.log.Timber;

public class UsersData {

    private void manageUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersData = db.collection("users-data");

        usersData.document("mockUserId").get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserProfile userProfile = documentSnapshot.toObject(UserProfile.class);
                        // Handle user profile data
                    } else {
                        Timber.tag("Admin").d("No such document");
                    }
                })
                .addOnFailureListener(e -> Timber.tag("Admin").e(e, "Error fetching user profile"));
    }


    private void viewMetrics() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference rankData = db.collection("rank-data");

        rankData.orderBy("totalScore", Query.Direction.DESCENDING).limit(30).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        RankEntry rankEntry = document.toObject(RankEntry.class);
                        // Handle rank entry data
                    }
                })
                .addOnFailureListener(e -> Timber.tag("Admin").e(e, "Error fetching metrics"));
    }


}