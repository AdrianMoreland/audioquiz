package com.audioquiz.data.local.dao;

import androidx.room.*;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public interface BaseDao<ENTITY> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(ENTITY item);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCompletable (ENTITY item);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insertAll(List<ENTITY> items);

    @Update
    void update(ENTITY item);

    @Update
    void update(List<ENTITY> items);

    @Delete
    void delete(ENTITY item);
}

