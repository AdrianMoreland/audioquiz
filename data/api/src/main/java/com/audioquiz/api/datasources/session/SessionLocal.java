package com.audioquiz.api.datasources.session;


import com.audioquiz.core.model.auth.Session;

public interface SessionLocal {
    Session getSession();

    void updateSession(Session session);

    void flushData();
}
