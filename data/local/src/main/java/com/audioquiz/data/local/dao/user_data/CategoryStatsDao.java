package com.audioquiz.data.local.dao.user_data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.audioquiz.data.local.dao.BaseDao;
import com.audioquiz.data.local.entity.user_data.CategoryStatsEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CategoryStatsDao extends BaseDao<CategoryStatsEntity> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertCompletable (CategoryStatsEntity categoryStats);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(CategoryStatsEntity categoryStats);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<CategoryStatsEntity> categoryStats);

    @Update
    void update(CategoryStatsEntity categoryStats);

    @Update
    void update(List<CategoryStatsEntity> categoryStats);

    @Query("SELECT * FROM category_stats WHERE categoryName = :categoryName")
    CategoryStatsEntity getCategoryStatsByCategoryName(String categoryName);

    @Query("SELECT * FROM category_stats")
    Single<List<CategoryStatsEntity>> getCategoryStatsDataList();
}