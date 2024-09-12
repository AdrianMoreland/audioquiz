package com.audioquiz.localdatasource.dao.rank;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.adrian.database.dao.BaseDao;
import com.adrian.database.entity.rank_stats.RankAllTimeEntity;

import java.util.List;

@Dao
public interface RankAllTimeDao extends BaseDao<RankAllTimeEntity> {
    @Query("SELECT * FROM rankAllTime ORDER BY totalScore DESC")
    List<RankAllTimeEntity> getAllRankEntries();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<RankAllTimeEntity> rankEntries);

    @Query("DELETE FROM rankAllTime")
    void deleteAll();
}
