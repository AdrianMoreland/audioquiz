package com.audioquiz.admin.domain;

import com.google.android.gms.tasks.Task;

public interface IAnalyticsData {
    public Task<Object> generateUserActivityReport(String userId);
    public Task<Object> generateUsageStatistics();

}