package com.audioquiz.admin.domain;

public interface IMockData {
    public void addMockData(String userId, Object dataType);
    public void removeMockData(String userId, Object dataType);

    public void addPrecomputedScore(String userId, Object score);
    public void updatePrecomputedScore(String userId, Object updatedScore);
    public void deletePrecomputedScore(String userId);

}