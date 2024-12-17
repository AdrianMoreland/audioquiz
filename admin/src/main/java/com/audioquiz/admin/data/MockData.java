package com.audioquiz.admin.data;

import com.audioquiz.admin.domain.IMockData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

public class MockData implements IMockData {


    private void addMockData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersData = db.collection("users-data");

        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("lastUpdated", System.currentTimeMillis());
        userProfile.put("userId", "mockUserId");
        userProfile.put("username", "MockUser");
        userProfile.put("profileImage", "http://example.com/profile.jpg");
        userProfile.put("dateCreated", System.currentTimeMillis());

        usersData.document("mockUserId").set(userProfile)
                .addOnSuccessListener(aVoid -> Timber.tag("Admin").d("Mock data added"))
                .addOnFailureListener(e -> Timber.tag("Admin").e(e, "Error adding mock data"));
    }


    @Override
    public void addMockData(String userId, Object dataType) {

    }

    @Override
    public void removeMockData(String userId, Object dataType) {

    }

    @Override
    public void addPrecomputedScore(String userId, Object score) {

    }

    @Override
    public void updatePrecomputedScore(String userId, Object updatedScore) {

    }

    @Override
    public void deletePrecomputedScore(String userId) {

    }
}