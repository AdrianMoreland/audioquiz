package com.audioquiz.api.datasources.firebase_auth;

import com.audioquiz.core.model.user.UserProfile;

public interface AuthLocal {
    UserProfile getUser();
}
