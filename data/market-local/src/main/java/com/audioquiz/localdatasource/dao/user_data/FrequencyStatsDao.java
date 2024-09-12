package com.audioquiz.localdatasource.dao.user_data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.adrian.database.dao.BaseDao;
import com.adrian.database.entity.user_data.frequency_stats.PitchStatsEntity;

import java.util.List;

@Dao
public interface FrequencyStatsDao extends BaseDao<PitchStatsEntity> {

    @Query("SELECT * FROM frequency_stats_data WHERE frequency = :frequency")
    PitchStatsEntity getPitchStatsByFrequency(int frequency);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertPitchScore(PitchStatsEntity pitchScore);

    @Update
    int updatePitchScore(PitchStatsEntity pitchScore);

    @Delete
    int deletePitchScore(PitchStatsEntity pitchScore);

    @Query("SELECT * FROM frequency_stats_data")
    List<PitchStatsEntity> getAllPitchScores();

    @Query("SELECT * FROM frequency_stats_data WHERE id = :id")
    LiveData<PitchStatsEntity> getPitchScoreById(int id);
}
