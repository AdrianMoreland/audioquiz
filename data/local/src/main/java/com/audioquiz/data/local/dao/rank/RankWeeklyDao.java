package com.audioquiz.data.local.dao.rank;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.audioquiz.data.local.dao.BaseDao;
import com.audioquiz.data.local.entity.rank_stats.RankWeeklyEntity;

import java.util.List;

@Dao
public interface RankWeeklyDao extends BaseDao<RankWeeklyEntity> {
    @Query("SELECT * FROM rankWeekly ORDER BY totalScore DESC")
    List<RankWeeklyEntity> getAllRankEntries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<RankWeeklyEntity> rankEntries);

    @Query("DELETE FROM rankWeekly")
    void deleteAll();
}
