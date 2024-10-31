package com.audioquiz.data.local.dao.user_data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.audioquiz.data.local.dao.BaseDao;
import com.audioquiz.data.local.entity.user_data.frequency_stats.IntervalStatsEntity;

import java.util.List;

@Dao
public interface IntervalStatsDao extends BaseDao<IntervalStatsEntity> {

    @Query("SELECT * FROM interval_stats_data WHERE frequency = :frequency")
    IntervalStatsEntity getIntervalScoreByFrequency(int frequency);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertIntervalScore(IntervalStatsEntity IntervalScore);

    @Update
    int updateIntervalScore(IntervalStatsEntity IntervalScore);

    @Delete
    int deleteIntervalScore(IntervalStatsEntity IntervalScore);

    @Query("SELECT * FROM interval_stats_data")
    List<IntervalStatsEntity> getAllIntervalScores();

    @Query("SELECT * FROM interval_stats_data WHERE id = :id")
    LiveData<IntervalStatsEntity> getIntervalScoreById(int id);
}
