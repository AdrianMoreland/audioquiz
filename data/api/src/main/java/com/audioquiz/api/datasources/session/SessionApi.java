package com.audioquiz.api.datasources.session;


import com.audioquiz.core.model.auth.AccessToken;
import com.audioquiz.core.model.auth.Session;

public interface SessionApi {
    Session createSession(AccessToken accessToken);
}
