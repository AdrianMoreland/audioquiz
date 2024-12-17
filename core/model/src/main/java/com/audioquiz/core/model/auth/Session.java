package com.audioquiz.core.model.auth;

/**
 * Represents a session related to the user that is using the application.
 * [success] represents the state of the session creation.
 * [session_id] represents the identifier of the session (the actual session).
 */
public class Session {
    Boolean success;
    String sessionId;

    public Session(Boolean success, String sessionId) {
        this.success = success;
        this.sessionId = sessionId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setSessionId(String session_id) {
        this.sessionId = session_id;
    }
}
