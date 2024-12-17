package com.audioquiz.api.datasources.tokens;


import com.audioquiz.core.model.auth.AccessToken;

public interface AccessTokenApi {
    /**
     * @return an [AccessToken] retrieved from the server when possible.
     * Null if an error occurs.
     */
    AccessToken getAccessToken();
}
