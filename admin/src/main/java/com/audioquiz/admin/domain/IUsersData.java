package com.audioquiz.admin.domain;

import com.audioquiz.core.model.user.UserProfile;
import com.google.android.gms.tasks.Task;

import java.util.List;

public interface IUsersData {
    void manageUsers();

    void viewMetrics();

    public void addUser(UserProfile userProfile);
    public void updateUser(String userId, UserProfile updatedProfile);
    public void deleteUser(String userId);
    public Task<UserProfile> getUser(String userId);

    public Task<List<UserProfile>> listUsers(int limit, int offset);
    public Task<List<UserProfile>> searchUsers(String query);

}