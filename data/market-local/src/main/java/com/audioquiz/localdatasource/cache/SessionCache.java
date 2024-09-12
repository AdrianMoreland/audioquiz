package com.audioquiz.localdatasource.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.adrian.api.data.datasources.session.SessionLocal;
import com.adrian.model.login.Session;

public class SessionCache implements SessionLocal {


    private static final String PREFERENCES_FILE_NAME = "com.jpp.moviespreview.preferences.session";
    private static final String KEY_SESSION_STORED = "session_stored";

    private final SharedPreferences preferences;

    public SessionCache(Context context) {
        this.preferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public Session getSession() {
        String sessionId = preferences.getString(KEY_SESSION_STORED, null);
        if (sessionId != null) {
            return new Session(true, sessionId);
        }
        return null;
    }

    @Override
    public void updateSession(Session session) {
        preferences.edit()
                .putString(KEY_SESSION_STORED, session.getSessionId())
                .apply();
    }

    @Override
    public void flushData() {
        preferences.edit()
                .putString(KEY_SESSION_STORED, null)
                .apply();
    }
}
