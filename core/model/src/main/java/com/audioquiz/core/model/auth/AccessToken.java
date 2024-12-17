package com.audioquiz.core.model.auth;

/**
 * Represents an access token used for authentication.
 */
public class AccessToken {
    private final boolean success;
    private final String expires_at;
    private final String request_token;

    /**
     * Initializes a new AccessToken with the provided parameters.
     *
     * @param success Indicates whether the token request was successful.
     * @param expires_at The expiration date of the token.
     * @param request_token The request token string.
     */
    public AccessToken(boolean success, String expires_at, String request_token) {
        this.success = success;
        this.expires_at = expires_at;
        this.request_token = request_token;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getExpiresAt() {
        return expires_at;
    }

    /**
     * @return The request token string associated with this access token.
     */
    public String getRequestToken() {
        return request_token;
    }
}
