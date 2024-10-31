package com.audioquiz.data.local.dao.user_data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.audioquiz.data.local.dao.BaseDao;
import com.audioquiz.data.local.entity.user_data.GeneralStatsEntity;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface GeneralStatsDao extends BaseDao<GeneralStatsEntity> {

    @Transaction
    @Query("SELECT * FROM general_stats LIMIT 1")
    Single<GeneralStatsEntity> getGeneralStatsSingle();

    @Query("SELECT * FROM general_stats WHERE userId = :userId")
    GeneralStatsEntity findByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUserStats(GeneralStatsEntity stats);

    @Query("DELETE FROM general_stats WHERE userId = :userId")
    Completable deleteGeneralStats(String userId);
}
